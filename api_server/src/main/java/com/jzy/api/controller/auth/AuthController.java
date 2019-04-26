package com.jzy.api.controller.auth;

import com.jzy.framework.controller.GenericController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>功能描述：</b>权限控制<br>
 * <b>修订记录：</b><br>
 * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
 */
@Slf4j
@Controller
@RequestMapping(path="/auth")
public class AuthController {

    /**
     * <b>功能描述：</b>获取用户资源列表<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path="/login")
    public String login(HttpServletRequest request){
        UsernamePasswordToken  token = new UsernamePasswordToken("xt", "123456");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        token.setRememberMe(false);
        //如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        String url = request.getRequestURI();
        if (exceptionClassName != null) {
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                return "UnknownAccountException authenFail";
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                return "IncorrectCredentialsException authenFail";
            }
            return "authenFail";
        }
        if (subject.isAuthenticated()) {
            return "already login";
        } else {
            return "please login";
        }
    }

}
