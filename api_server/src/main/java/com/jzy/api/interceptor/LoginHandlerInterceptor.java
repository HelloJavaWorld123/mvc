package com.jzy.api.interceptor;

import com.jzy.api.annos.WithoutLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * <b>功能：</b>拦截器<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  Object handler) {
        // 请求参数日志
        logRequestParam(request);
        if (handler instanceof HandlerMethod) {
            // 请求方法存在免登录注解的不拦截
            WithoutLogin loginAnnotation = ((HandlerMethod) handler).getMethodAnnotation(WithoutLogin.class);
            // 如果存在WithoutLogin注解，则不需要登录
            if (loginAnnotation != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * <b>功能描述：</b>请求参数日志<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void logRequestParam(HttpServletRequest request) {
        if (log.isInfoEnabled()) {
            StringBuilder headers = new StringBuilder();
            StringBuilder values = new StringBuilder();
            Enumeration<String> headerNames = request.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                if (name != null) {
                    headers.append(name);
                    headers.append(" | ");
                    String value = request.getHeader(name);
                    if (value != null) {
                        values.append(value);
                        values.append(" | ");
                    }
                }
            }
            log.debug("请求地址:{}, 请求头:{}, 请求值:{}", request.getRequestURI(), headers.toString(), values.toString());
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,  Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,  Object handler, Exception e) {

    }
}
