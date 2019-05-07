package com.jzy.api.controller.auth;

import com.jzy.api.model.auth.Auth;
import com.jzy.api.service.auth.AuthService;
import com.jzy.api.vo.auth.AuthVo;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <b>功能描述：</b>权限控制<br>
 * <b>修订记录：</b><br>
 * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
 */
@Slf4j
@Controller
@RequestMapping(path="/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * <b>功能描述：</b>获取用户资源列表<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path="/queryMenuList")
    public ApiResult queryMenuList(){
        ApiResult<AuthVo> apiResult = new ApiResult<>();
        List<Auth> auth = authService.queryMenuList();
        AuthVo authVo = new AuthVo();
        return apiResult.success(authVo);
    }

    /**
     * <b>功能描述：</b>遍历树形结构<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void forEachAuth() {

    }

}
