package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.WxOAuthCnd;
import com.jzy.api.model.biz.SecurityToken;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyHttp;
import com.jzy.api.service.wx.WXPayUtil;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(path = "callback.shtml", method = RequestMethod.GET)
    public ModelAndView wxCallback(@RequestParam(defaultValue = "") String code,
                                       HttpServletRequest req, HttpServletResponse resp, ModelMap model) {
        ModelAndView view = new ModelAndView("login");
        // LoginUserMapper loginUser = LoginUserMapper.getLoginUser(req.getSession());
        model.put("wechat_oauth_url", wxPayService.getUrlByAuthType("oauth"));
        // 是否为微信内置浏览器
        model.put("isWechat", CommUtils.iswechat(req));
        if (!"authdeny".equals(code)) {
            // 通过code换取网页授权access_token
            SecurityToken securityToken = wxPayService.querySecurityToken(code);
            return view;
        }
        // 用户未授权返回提示用户取消授权
        log.error("微信网页授权获取用户信息失败-----------------------");
        model.put("errmsg", "用户取消授权");
        return view;
    }


    /**
     * <b>功能描述：</b>支付异步回调<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("wx/notify.shtml")
    public String wxCallback(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(MyHttp.readRequestData(req));
        return wxPayService.updateWxCallBack(notifyMap);
    }

    /**
     * <b>功能描述：</b>H5支付同步回调返回,  中间确认页<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("/webapp_return.shtml")
    public ModelAndView wxWebappReturn(HttpServletRequest req, HttpServletResponse resp, @RequestParam String orderId) {
        return new ModelAndView("/home/wxpay_webapp.jsp?orderId=".concat(orderId));
    }


}
