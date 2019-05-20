package com.jzy.api.controller.biz;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.biz.WxOAuthCnd;
import com.jzy.api.cnd.biz.WxSdkCnd;
import com.jzy.api.constant.PayConfig;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.service.wx.WXPayUtil;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyHttp;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
@RequestMapping(path="/wx")
public class WxPayController extends GenericController {

    @Resource
    private WxPayService wxPayService;

    /**
     * 获取微信 JS-SDK 的配置
     * jiazk 2019年5月18日
     */
    @ResponseBody
    @RequestMapping(path="/weChatSdk", method = RequestMethod.POST)
    public ApiResult weChatSdk(@RequestBody WxSdkCnd wxSdkCnd) {
        Map<String,String> sdkConfig = wxPayService.getSdkConfig(wxSdkCnd.getUrl());
        log.debug("getSdkConfig  -- " + jsonConverter.toJson(sdkConfig));
        return new ApiResult<>(sdkConfig);
    }

    /**
     * <b>功能描述：</b>微信授权<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path="/wxOAuth", method = RequestMethod.POST)
    public ApiResult wxOAuth(@RequestBody WxOAuthCnd wxOAuthCnd) {
        if (!CommUtils.exist(new String[]{"oauth", "qroauth"}, wxOAuthCnd.getType())) {
            throw new BusException("授权类型不存在");
        }
        String authorizeUrl = wxPayService.getUrlByAuthType(wxOAuthCnd.getType());
        log.debug("authorizeUrl  -- " + authorizeUrl);
        return new ApiResult<>(authorizeUrl);
    }

    /**
     * <b>功能描述：</b>授权回调<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @WithoutLogin
    @RequestMapping(path = "/authCallback", method = RequestMethod.GET)
    public ModelAndView authCallback(@RequestParam(defaultValue = "") String code, @RequestParam(defaultValue = "") String state,
                                       HttpServletRequest req, HttpServletResponse resp, ModelMap model) throws IOException {
        if (!"authdeny".equals(code)) {
            wxPayService.updateSecurityToken(code, state);
            return new ModelAndView(new RedirectView(PayConfig.getH5DomainUrl()));
        }
        // 用户未授权返回提示用户取消授权
        log.error("微信网页授权获取用户信息失败-----------------------");
        model.put("errmsg", "用户取消授权");
        return new ModelAndView(PayConfig.getH5DomainUrl());
    }

    /**
     * <b>功能描述：</b>支付异步回调<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @WithoutLogin
    @RequestMapping("/payCallback")
    public String payCallback(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        log.debug("wxPayCallBack");
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(MyHttp.readRequestData(req));
        log.debug("wx callback param is " + notifyMap.toString());
        return wxPayService.updateWxCallBack(notifyMap);
    }

}
