package com.jzy.api.service.biz.impl;

import com.jzy.api.constant.SupConfig;
import com.jzy.api.constant.WXPayConfig;
import com.jzy.api.constant.WXPayConstants;
import com.jzy.api.dao.biz.OrderMapper;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.TradeRecord;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.SupService;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.service.wx.WXPay;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.WXPayUtil;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.exception.PayException;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jzy.api.constant.WXPayConstants.*;
import static com.jzy.api.constant.WechatConstant.*;
import static com.jzy.api.model.biz.TradeRecord.RecordConst.*;

/**
 * <b>功能：</b>支付宝支付业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190419&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    private static final String SYSTEM_OPERATION = "System Operation";

    /**
     * 微信appId
     */
    @Value("${wx_app_id}")
    private String appId;
    /**e
     * 微信appSecret
     */
    @Value("${wx_redirect_uri}")
    private String wxRedirectUri;
    /**
     * 回调地址
     */
    @Value("${wx_app_secret}")
    private String appSecret;

    @Resource
    private OrderService orderService;

    @Resource
    private TradeRecordService tradeRecordService;

    @Resource
    private SupService supService;

    @Override
    public String updateWxCallBack(Map<String, String> notifyMap) throws Exception {
        String returnCode = FAIL;
        WXPay wxpay = new WXPay(WXPayConfig.getInstance(), true);
        // 加密信息，仅申请退款结果通知时有该参数
        String reqInfo = notifyMap.get("req_info");
        if (StringUtils.isEmpty(reqInfo)) {
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) { // 签名正确
                String outTradeNo = notifyMap.get("out_trade_no");
                String transactionId = notifyMap.get("transaction_id");
                String totalFee = notifyMap.get("total_fee");
                BigDecimal tradeFee =  new BigDecimal(WXPayUtil.changeF2Y(totalFee));
                String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
                tradeRecordService.updateBgRespByIdStatus(transactionId, notifyMap.get("result_code").equalsIgnoreCase(SUCCESS) ? STATUS_PASSED : STATUS_FAILED, notifyMap.toString(), notifyMap.get("attach"), STATUS_WAITED);
                // 业务处理
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                if (notifyMap.get("result_code").equalsIgnoreCase(SUCCESS)) {
                    notifySuccessPay(orderId, transactionId, tradeFee);
                } else {
                    orderService.tradeRefund(orderService.queryOrderById(orderId));
                }
                returnCode = SUCCESS;
                log.debug("：：：Wechat Notify Sign Verify Success.".concat(notifyMap.toString()));
            } else { // 签名错误，如果数据里没有sign字段，也认为是签名错误
                log.debug("：：：Wechat Notify Sign Verify Failed. " + notifyMap.toString());
                throw new PayException(notifyMap.toString());
            }
        } else {
            // 解密req_info
            String decryptReqInfo = WXPayUtil.decrypt(reqInfo);
            Map<String, String> reqInfoMap = WXPayUtil.xmlToMap(decryptReqInfo);
            log.debug("：：：Wechat Refund Notify Result. ".concat(decryptReqInfo));
            boolean refundStatus = reqInfoMap.get("refund_status").equalsIgnoreCase(SUCCESS);
            String outRefundNo = reqInfoMap.get("out_refund_no");
            String outTradeNo = reqInfoMap.get("out_trade_no");
            String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
            tradeRecordService.updateBgRespByOperatorStatus(reqInfoMap.get("refund_id"), refundStatus ? STATUS_PASSED : STATUS_FAILED, reqInfoMap.toString(), outRefundNo, STATUS_WAITED);

            if (refundStatus) {
                orderService.updateTradeStatus(orderId, Order.TradeStatusConst.REFUND_SICCESS);
                returnCode = SUCCESS;
            } else {
                // 重新提交退款申请(一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号)
                refundOrderwx(reqInfoMap.get("out_trade_no"), outRefundNo, new BigDecimal(reqInfoMap.get("total_fee")), new BigDecimal(reqInfoMap.get("refund_fee")));
                log.info("：：：微信退款失败，重新申请退款.退款单号:" + outRefundNo);
            }
        }
        return returnXML(returnCode);
    }


    /**
     * <b>功能描述：</b>xxx<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private String notifySuccessPay(String orderId, String transactionId, BigDecimal payTotalFee) {
        String result = null;
        try {
            Order order = orderService.queryOrderById(orderId);
            if (order.getSupStatus() != 0) {
                return SupConfig.SUP_STATUS_03;
            }
            order.setStatus(1);
            order.setTradeCode(transactionId);
            order.setTradeFee(payTotalFee);
            order.setTradeStatus(Order.TradeStatusConst.PAY_SUCCESS);
            orderService.update(order);
            // 提交订单到SUP
            supService.commitOrderToSup(order);
        } catch (Exception e) {
            log.error("支付成功订单提交失败,订单id:".concat(orderId).concat("异常信息:"), e);
        }
        return result;
    }

    /**
     * <b>功能描述：</b>支付<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public ApiResult pay(Order order) {
        return null;
    }

    /**
     * <b>功能描述：</b>退单<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public boolean orderBack(Order order) {
        order.setStatus(3);
        order.setSupStatus(3);
        Map<String, String> wechatRes = refundOrderwx(order.getOutTradeNo(), order.getTotalFee());
        if (WXPayConstants.SUCCESS.equals(wechatRes.get(WXPayConstants.RESULT_CODE))) {
            order.setTradeStatus(Order.TradeStatusConst.WAIT_REFUND);
        } else {
            log.error(order.getId() + "订单微信申请退款失败:" + wechatRes.get(WXPayConstants.ERR_CODE) + "/" + wechatRes.get(WXPayConstants.ERR_CODE_DES));
        }
        return false;
    }

    @Override
    public String getUrlByAuthType(String oauthType) {
        return getUrlByAuthType(oauthType, null);
    }

    @Override
    public String getUrlByAuthType(String oauthType, String uri) {
        String authorizeUrl = null;
        switch (oauthType) {
            case "baseoauth":
                authorizeUrl = authorize_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(uri))
                        .replace("SCOPE", SCOPE_SNSAPI_BASE)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
            case "oauth":
                authorizeUrl = authorize_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(wxRedirectUri))
                        .replace("SCOPE", SCOPE_SNSAPI_USERINFO)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
            case "qroauth":
                authorizeUrl = website_oauth_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(wxRedirectUri))
                        .replace("SCOPE", SCOPE_SNSAPI_LOGIN)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
        }
        return authorizeUrl;
    }

    private Map<String, String> refundOrderwx(String out_trade_no, BigDecimal total_fee) {
        return this.refundOrderwx(out_trade_no, "", total_fee, total_fee);
    }

    private Map<String, String> refundOrderwx(String out_trade_no, String out_refund_no, BigDecimal total_fee, BigDecimal refund_fee) {
        return this.refundOrderwx(out_trade_no, out_refund_no, total_fee, refund_fee, null);
    }

    private Map<String, String> refundOrderwx(String out_trade_no, String out_refund_no, BigDecimal total_fee, BigDecimal refund_fee, String refund_desc) {
        Map<String, String> respmap = null, params = new HashMap<>();
        try {
            params.put("out_trade_no", out_trade_no);
            params.put("out_refund_no", StringUtils.isEmpty(out_refund_no) ? out_trade_no : out_refund_no);
            params.put("total_fee", total_fee.multiply(new BigDecimal(100)) + "");
            params.put("refund_fee", refund_fee.multiply(new BigDecimal(100)) + "");
            params.put("refund_desc", StringUtils.isEmpty(refund_desc) ? SYSTEM_OPERATION : refund_desc);
            WXPay wxpay = new WXPay(WXPayConfig.getInstance());
            respmap = wxpay.refund(params);
            boolean resultStatus = WXPayConstants.SUCCESS.equalsIgnoreCase(respmap.get("result_code"));

            log.debug("：：：wechat refund order result.".concat(respmap.toString()));

            respmap.put("trade_record_id", CommUtils.lowerUUID());

            String refundUrl = wxpay.isUseSandbox() ? SANDBOX_REFUND_URL_SUFFIX : REFUND_URL_SUFFIX;
            tradeRecordService.insert(new TradeRecord(respmap.get("trade_record_id"), params.get("out_refund_no"), new Date(), refundUrl, params.toString(), resultStatus ? STATUS_WAITED : STATUS_FAILED, TYPE_REFUND, new Date(), respmap.toString(), TRUSTEESHIP_WECHAT));
        } catch (Exception e) {
            log.error("：：：Err - Wechat Refund 申请退款.", e);

            throw new BusException("申请退款异常");
        }
        return respmap;
    }

    /**
     * wechat:返回给微信服务端的Xml
     * @param returnCode [SUCCESS,FAIL]
     * @return
     */
    private String returnXML(String returnCode) {
        return "<xml><return_code><![CDATA[".concat(returnCode).concat("]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
    }

}
