package com.jzy.api.service.biz.impl;

import com.jzy.api.constant.WXPayConfig;
import com.jzy.api.constant.WXPayConstants;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.TradeRecord;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.service.wx.WXPay;
import com.jzy.api.util.CommUtils;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
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
    private TradeRecordService tradeRecordService;

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
}
