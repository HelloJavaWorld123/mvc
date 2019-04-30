package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.WxOAuthCnd;
import com.jzy.api.constant.WXPayConfig;
import com.jzy.api.model.biz.WXPay;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyHttp;
import com.jzy.api.util.WXPayUtil;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.jzy.api.constant.WXPayConstants.FAIL;
import static com.jzy.api.constant.WXPayConstants.SUCCESS;

/**
 * <b>功能：</b>微信支付<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Controller
@RequestMapping(path="/wxPay")
public class WxPayController extends GenericController {

    @Resource
    private WxPayService wxPayService;

    /**
     * <b>功能描述：</b>微信授权<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path="/wxOAuth")
    public ApiResult wxOAuth(@RequestBody WxOAuthCnd wxOAuthCnd) {
        if (!CommUtils.exist(new String[]{"oauth", "qroauth"}, wxOAuthCnd.getType())) {
            throw new BusException("授权类型不存在");
        }
        String authorizeUrl = wxPayService.getUrlByAuthType(wxOAuthCnd.getType());
        log.debug("authorizeUrl  -- " + authorizeUrl);
        return new ApiResult<>(authorizeUrl);
    }

    /**
     * <b>功能描述：</b>支付异步回调<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("wx/notify.shtml")
    public String wxcallback(HttpServletRequest req, HttpServletResponse resp) {
        String returnCode = FAIL;
//        try {
//            Map<String, String> notifyMap = WXPayUtil.xmlToMap(MyHttp.readRequestData(req));
//            log.info("：：：Wechat Notify Request." + notifyMap.toString());
//
//            WXPay wxpay = new WXPay(WXPayConfig.getInstance(), true);
//            // 加密信息，仅申请退款结果通知时有该参数
//            String reqInfo = notifyMap.get("req_info");
//            if (StringUtils.isEmpty(reqInfo)) {
//                if (wxpay.isPayResultNotifySignatureValid(notifyMap)) { // 签名正确
//                    /**
//                     * 公众账号ID	        appid	            是	String(32)	wx8888888888888888	    微信分配的公众账号ID（企业号corpid即为此appId）
//                     * 商户号      	    mch_id	            是	String(32)	1900000109	            微信支付分配的商户号
//                     * 设备号	            device_info	        否	String(32)	013467007045764	        微信支付分配的终端设备号，
//                     * 随机字符串	        nonce_str	        是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位
//                     * 签名	            sign	            是	String(32)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名算法
//                     * 签名类型	        sign_type	        否	String(32)	HMAC-SHA256	        签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
//                     * 业务结果	        result_code	        是	String(16)	SUCCESS	            SUCCESS/FAIL
//                     * 错误代码	        err_code	        否	String(32)	SYSTEMERROR	        错误返回的信息描述
//                     * 错误代码描述	    err_code_des	    否	String(128)	系统错误	            错误返回的信息描述
//                     * 用户标识	        openid	            是	String(128)	wxd930ea5d5a258f4f	用户在商户appid下的唯一标识
//                     * 是否关注公众账号	is_subscribe	    是	String(1)	Y	                用户是否关注公众账号，Y-关注，N-未关注
//                     * 交易类型	        trade_type	        是	String(16)	JSAPI	            JSAPI、NATIVE、APP
//                     * 付款银行	        bank_type	        是	String(16)	CMC	                银行类型，采用字符串类型的银行标识，银行类型见银行列表
//                     * 订单金额	        total_fee	        是	Int	        100	                订单总金额，单位为分
//                     * 应结订单金额	    settlement_total_fee否	Int	        100	                应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
//                     * 货币种类	        fee_type	        否	String(8)	CNY	                货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
//                     * 现金支付金额	    cash_fee	        是	Int	        100	                现金支付金额订单现金支付金额，详见支付金额
//                     * 现金支付货币类型	cash_fee_type	    否	String(16)	CNY	                货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
//                     * 总代金券金额	    coupon_fee	        否	Int	        10	                代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，详见支付金额
//                     * 代金券使用数量	    coupon_count	    否	Int	        1	                代金券使用数量
//                     * 代金券类型	        coupon_type_$n	    否	String	    CASH	            CASH--充值代金券,NO_CASH---非充值代金券.并且订单使用了免充值券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_0
//                     * 代金券ID	        coupon_id_$n	    否	String(20)	10000	            代金券ID,$n为下标，从0开始编号
//                     * 单个代金券支付金额	coupon_fee_$n	    否	Int	        100	                单个代金券支付金额,$n为下标，从0开始编号
//                     * 微信支付订单号	    transaction_id	    是	String(32)	1217752501201407033233368018	微信支付订单号
//                     * 商户订单号	        out_trade_no	    是	String(32)	1212321211201407033568112322	商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
//                     * 商家数据包	        attach	            否	String(128)	123456	            商家数据包，原样返回
//                     * 支付完成时间	    time_end	        是	String(14)	20141030133525	    支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//                     */
//                    String outTradeNo = notifyMap.get("out_trade_no");
//                    String transactionId = notifyMap.get("transaction_id");
//                    String totalFee = notifyMap.get("total_fee");
//                    Double tradeFee = Double.valueOf(WXPayUtil.changeF2Y(totalFee));
//                    String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
//
//                    iTradeRecordService.updateBgRespByIdStatus(transactionId, notifyMap.get("result_code").equalsIgnoreCase(SUCCESS) ? STATUS_PASSED : STATUS_FAILED, notifyMap.toString(), notifyMap.get("attach"), STATUS_WAITED);
//                    // 业务处理
//                    // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
//                    if (notifyMap.get("result_code").equalsIgnoreCase(SUCCESS)) {
//                        notifySuccessPay(orderId ,transactionId ,tradeFee);
////                        String supStatus = notifySuccessPay(orderId, transactionId, tradeFee);
////                        // 请求SUP下订单,01成功,02失败,03已经提交过订单
////                        if (supStatus.equals(SupConfig.SUP_STATUS_02)) {
////                            iPayService.refundOrderwx(outTradeNo, tradeFee);
////                        }
//                    } else {
//                        iOrderService.tradeRefund(iOrderService.queryOrderById(orderId));
////                        iOrderService.updateStatusTrade(orderId, 3, transactionId, tradeFee, OrderMapper.TradeStatusConst.WAIT_REFUND);
////                        iPayService.refundOrderwx(outTradeNo, Double.valueOf(WXPayUtil.changeF2Y(totalFee)));
//                    }
//                    returnCode = SUCCESS;
//                    log.debug("：：：Wechat Notify Sign Verify Success.".concat(notifyMap.toString()));
//                } else { // 签名错误，如果数据里没有sign字段，也认为是签名错误
//                    log.info("：：：Wechat Notify Sign Verify Failed. " + notifyMap.toString());
//                }
//            } else {
//                // 解密req_info
//                String decryptReqInfo = WXPayUtil.decrypt(reqInfo);
//                Map<String, String> reqInfoMap = WXPayUtil.xmlToMap(decryptReqInfo);
//                log.debug("：：：Wechat Refund Notify Result. ".concat(decryptReqInfo));
//                /**
//                 * 微信订单号        transaction_id          是       String(32)       1217752501201407033233368018    微信订单号
//                 * 商户订单号        out_trade_no            是       String(32)       1217752501201407033233368018    商户系统内部的订单号
//                 * 微信退款单号       refund_id               是       String(32)      1217752501201407033233368018    微信退款单号
//                 * 商户退款单号       out_refund_no           是       String(64)      1217752501201407033233368018    商户退款单号
//                 * 订单金额          total_fee               是       Int             100                    订单总金额，单位为分，只能为整数，详见支付金额
//                 * 应结订单金额       settlement_total_fee    否       Int             100                    当该订单有使用非充值券时，返回此字段。应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
//                 * 申请退款金额       refund_fee              是       Int             100                    退款总金额,单位为分
//                 * 退款金额         settlement_refund_fee    是       Int             100                  webapp_result  退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
//                 * 退款状态         refund_status            是       String(16)      SUCCESS                SUCCESS-退款成功，CHANGE-退款异常，REFUNDCLOSE—退款关闭
//                 * 退款成功时间      success_time             否       String(20)      2017-12-15 09:46:01             资金退款至用户帐号的时间，格式2017-12-15 09:46:01
//                 * 退款入账账户       refund_recv_accout      是       String(64)      招商银行信用卡0403                 取当前退款单的退款入账方。1）退回银行卡：{银行名称}{卡类型}{卡尾号}；2）退回支付用户零钱:支付用户零钱；3）退还商户:商户基本账户，商户结算银行账户；4）退回支付用户零钱通:支付用户零钱通
//                 * 退款资金来源       refund_account          是       String(30)      REFUND_SOURCE_RECHARGE_FUNDS    REFUND_SOURCE_RECHARGE_FUNDS 可用余额退款/基本账户;REFUND_SOURCE_UNSETTLED_FUNDS 未结算资金退款
//                 * 退款发起来源       refund_request_source   是       String(30)      API                             API接口    VENDOR_PLATFORM商户平台
//                 */
//                boolean refundStatus = reqInfoMap.get("refund_status").equalsIgnoreCase(SUCCESS);
//                String outRefundNo = reqInfoMap.get("out_refund_no");
//                String outTradeNo = reqInfoMap.get("out_trade_no");
//                String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
////                iTradeRecordService.updateBgRespByOperatorStatus(reqInfoMap.get("refund_id"), refundStatus ? STATUS_PASSED : STATUS_FAILED, reqInfoMap.toString(), outRefundNo, STATUS_WAITED);
////
////                if (refundStatus) {
////                    iOrderService.updateTradeStatus(orderId, OrderMapper.TradeStatusConst.REFUND_SICCESS);
////                    returnCode = SUCCESS;
////                } else {
////                    // 重新提交退款申请(一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号)
////                    iPayService.refundOrderwx(reqInfoMap.get("out_trade_no"), outRefundNo, Integer.parseInt(reqInfoMap.get("total_fee")), Integer.parseInt(reqInfoMap.get("refund_fee")));
////
////                    logger.info("：：：微信退款失败，重新申请退款.退款单号:" + outRefundNo);
////                }
//            }
//        } catch (Exception e) {
//            log.error("：：：Err - 微信支付回调异常.", e);
//        }
        return returnXML(returnCode);
    }

    /**
     * wechat:返回给微信服务端的Xml
     * @param return_code [SUCCESS,FAIL]
     * @return
     */
    private String returnXML(String return_code) {
        return "<xml><return_code><![CDATA[".concat(return_code).concat("]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
    }

}
