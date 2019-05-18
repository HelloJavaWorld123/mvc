package com.jzy.api.constant;

import org.apache.http.client.HttpClient;

public class WXPayConstants {

    /**
     * 商家支付名称
     */
    public final static String BODY = "玖佰充值商城";

    public enum SignType {
        MD5, HMACSHA256
    }

    /** 支付类型  JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付 */
    public enum TradeType {
        JSAPI, NATIVE, APP, CASHPAY,MWEB
    }

    /**
     * 查询订单接口返回交易状态字段:trade_state
     * SUCCESS—支付成功
     *
     * REFUND—转入退款
     *
     * NOTPAY—未支付
     *
     * CLOSED—已关闭
     *
     * REVOKED—已撤销（付款码支付）
     *
     * USERPAYING--用户支付中（付款码支付）
     *
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    public enum TradeState {
        SUCCESS, REFUND, NOTPAY, CLOSED, REVOKED,USERPAYING,PAYERROR
    }


    public static final String DOMAIN_API = "api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "api2.mch.weixin.qq.com";
    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL = "FAIL";
    public static final String SUCCESS = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    /**
     * 通信标识
     */
    public static final String RETURN_CODE = "return_code";
    public static final String RETURN_MSG = "return_msg";

    /**
     * 业务标识
     */
    public static final String RESULT_CODE = "result_code";
    public static final String ERR_CODE = "err_code";
    public static final String ERR_CODE_DES = "err_code_des";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    public static final String MICROPAY_URL_SUFFIX = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL_SUFFIX = "/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX = "/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL_SUFFIX = "/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX = "/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL_SUFFIX = "/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX = "/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL_SUFFIX = "/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL_SUFFIX = "/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";

}
