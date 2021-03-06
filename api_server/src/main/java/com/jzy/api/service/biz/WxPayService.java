package com.jzy.api.service.biz;

import com.jzy.api.model.biz.SecurityToken;

import java.util.Map;

/**
 * <b>功能：</b>微信支付业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190419&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface WxPayService extends PayService {

    /**
     * <b>功能描述：</b>微信支付回调<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param notifyMap 微信支付返回参数
     */
    String updateWxCallBack(Map<String, String> notifyMap) throws Exception;

    /**
     * <b>功能描述：</b>根据授权类型获取授权的url地址<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param oauthType "oauth":内置网页授权, "qroauth":网站应用授权
     */
    String getUrlByAuthType(String oauthType);

    /**
     * <b>功能描述：</b>根据授权类型和url获取授权的url地址<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param oauthType "oauth":内置网页授权, "qroauth":网站应用授权
     */
    String getUrlByAuthType(String oauthType, String uri);

    /**
     * <b>功能描述：</b>根据code获取token<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    SecurityToken updateSecurityToken(String code, String state);

    /**
     * 获取微信 JS-SDK 的配置
     * jiazk 2019年5月18日
     */
    Map<String, String> getSdkConfig(String url);

    String getUrlByTypeAndyToken(String type, String tokenHeader);
}
