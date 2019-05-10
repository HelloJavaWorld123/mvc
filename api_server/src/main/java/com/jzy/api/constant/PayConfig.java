package com.jzy.api.constant;

import org.springframework.stereotype.Component;

@Component
public class PayConfig {
    /**
     * h5页面域名
     */
    private static String h5DomainUrl;
    /**
     * 服务端域名
     */
    private static String domainUrl;
    /**
     * 微信appId
     */
    private static String wxAppId;
    /**
     * 微信appsecret
     */
    private static String wxAppSecret;
    /**
     * 微信key
     */
    private static String wxKey;
    /**
     * 微信token
     */
    private static String wxToken;
    /**
     * 微信商户号
     */
    private static String wxMchId;
    /**
     * 微信回调地址（支付、退款）
     */
    private static String wxNotifyUrl;
    /**
     * 微信前端回调地址
     */
    private static String wxRedirectUrl;
    /**
     * 微信授权回调
     */
    private static String wxAuthCallbackUrl;
    /**
     * 阿里支付请求地址
     */
    private static String aliPayUrl;
    /**
     * 阿里支付异步回调
     */
    private static String aliNotifyUrl;

    public static String getDomainUrl() {
        return domainUrl;
    }

    public static void setDomainUrl(String domainUrl) {
        PayConfig.domainUrl = domainUrl;
    }

    public static String getWxAppId() {
        return wxAppId;
    }

    public static void setWxAppId(String wxAppId) {
        PayConfig.wxAppId = wxAppId;
    }

    public static String getWxKey() {
        return wxKey;
    }

    public static void setWxKey(String wxKey) {
        PayConfig.wxKey = wxKey;
    }

    public static String getWxMchId() {
        return wxMchId;
    }

    public static void setWxMchId(String wxMchId) {
        PayConfig.wxMchId = wxMchId;
    }

    public static String getWxNotifyUrl() {
        return wxNotifyUrl;
    }

    public static void setWxNotifyUrl(String wxNotifyUrl) {
        PayConfig.wxNotifyUrl = wxNotifyUrl;
    }
}
