package com.jzy.api.service.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.jzy.api.constant.WechatConstant;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.SecurityToken;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyHttp;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import static com.jzy.api.constant.WechatConstant.SCOPE_SNSAPI_LOGIN;
import static com.jzy.api.constant.WechatConstant.SCOPE_SNSAPI_USERINFO;

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

    /**
     * <b>功能描述：</b>根据授权类型获取授权的url地址<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param oauthType "oauth":内置网页授权, "qroauth":网站应用授权
     */
    @Override
    public String getUrlByAuthType(String oauthType) {
        return getUrlByAuthType(oauthType, null);
    }

    /**
     * <b>功能描述：</b>根据授权类型和url获取授权的url地址<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param oauthType "oauth":内置网页授权, "qroauth":网站应用授权
     */
    @Override
    public String getUrlByAuthType(String oauthType, String uri) {
        String authorizeUrl = null;
        switch (oauthType) {
            case "baseoauth":
                authorizeUrl = WechatConstant.authorize_url.replace("APPID", this.appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(uri))
                        .replace("SCOPE", WechatConstant.SCOPE_SNSAPI_BASE)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
            case "oauth":
                authorizeUrl = WechatConstant.authorize_url.replace("APPID", this.appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(this.wxRedirectUri))
                        .replace("SCOPE", SCOPE_SNSAPI_USERINFO)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
            case "qroauth":
                authorizeUrl = WechatConstant.authorize_url.replace("APPID", this.appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(this.wxRedirectUri))
                        .replace("SCOPE", SCOPE_SNSAPI_LOGIN)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
        }
        return authorizeUrl;
    }

    /**
     * <b>功能描述：</b>通过code换取网页授权access_token<br>
     * <b>修订记录：</b><br>
     * <li>20190423&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private SecurityToken getOAuthToken(String code) {
        SecurityToken oAuthToken = null;
        String requestUrl = WechatConstant.oauth_url.replace("APPID", this.appId).replace("SECRET", this.appSecret)
                .replace("CODE", code);
        log.debug("oauthurl:" + requestUrl);
        JSONObject result = null;
        try {
            result = JSONObject.parseObject(MyHttp.sendGet(requestUrl, null));
        } catch (Exception e) {
            log.error("：：：Wechat website gets oauth_token exception", e);
        }
        if (!StringUtils.isEmpty(result)) {
            if (null == result.getString(WechatConstant.ERRCODE)) {
                oAuthToken = new SecurityToken(CommUtils.upperUUID(), result.getString(WechatConstant.ACCESS_TOKEN),
                        result.getInteger(WechatConstant.EXPIRES_IN), result.getString(WechatConstant.REFRESH_TOKEN),
                        result.getString(WechatConstant.OPENID), result.getString(WechatConstant.SCOPE), 1, new Date());
//                try {
//                    iRedisService.setValue(OAUTH_WX_WEBSITE.concat(code), oAuthToken, result.getInteger(WechatConst.EXPIRES_IN));
//                } catch (Exception e) {
//                    logger.error("：：：Redis storing oauth_token exception", e);
//                }
            } else {
                log.error("：：：Wechat website failed to get oauth_token - errcode:" + result.getString(WechatConstant.ERRCODE) + ", errmsg:"
                        + result.getString(WechatConstant.ERRMSG));
            }
        }
        return oAuthToken;
    }

    /**
     * <b>功能描述：</b>支付<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public ApiResult pay(Order order) {
        return null;
    }
}
