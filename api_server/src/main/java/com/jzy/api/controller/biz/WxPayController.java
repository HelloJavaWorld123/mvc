package com.jzy.api.controller.biz;

import com.jzy.api.cnd.WxOAuthCnd;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <b>功能：</b>微信支付<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class WxPayController extends GenericController {

    @Resource
    private WxPayService wxPayService;

    /**
     * <b>功能描述：</b>微信授权<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping
    @ResponseBody
    public ApiResult wxOAuth(@RequestBody WxOAuthCnd wxOAuthCnd) {

        return new ApiResult();
    }

}
