package com.jzy.api.interceptor;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.constant.AccessToken;
import com.jzy.api.service.cache.CacheEmpService;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.cache.ContextHolder;
import com.jzy.framework.cache.EmpCache;
import com.jzy.framework.cache.ThreadLocalCache;
import com.jzy.framework.cache.UserCache;
import com.jzy.framework.exception.BusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
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

    @Resource
    private CacheEmpService cacheEmpService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  Object handler) {
        // 请求参数日志
        logRequestParam(request);
        if (handler instanceof HandlerMethod) {
            // 请求方法存在免登录注解的不拦截
//            WithoutLogin loginAnnotation = ((HandlerMethod) handler).getMethodAnnotation(WithoutLogin.class);
            // 如果存在WithoutLogin注解，则不需要登录
//            if (loginAnnotation != null) {
//                return true;
//            }
            // 设置全局变量
//            setTheadLocalCache(request);
            return true;
        }
        return false;
    }

    /**
     * <b>功能描述：</b>设置全局变量<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void setTheadLocalCache(HttpServletRequest request) {
        // 用于判断所请求的接口时前台还是后端登录；1：前台；2：后端；为空的情况代表的是前台
        String appType = request.getHeader(AccessToken.APP.getValue());
        if (StringUtils.isEmpty(appType)) {
            appType = "1";
        }
        ContextHolder contextHolder = new ContextHolder();
        if (!"1".equals(appType)) {
            // 从请求头中获取后端登录标识
            String accessTokenEmp = request.getHeader(AccessToken.EMP.getValue());
            // 校验token是否为空
            validateToken(accessTokenEmp);
            EmpCache empCache = cacheEmpService.getCacheEmpByKey(accessTokenEmp);
            // 校验缓存当中的值是否存在
            validateCache(empCache);
            contextHolder.setEmpCache(empCache);
        } else {
            String accessTokenUser = request.getHeader(AccessToken.USER.getValue());
            // 校验token是否为空
            validateToken(accessTokenUser);
            UserCache userCache = cacheEmpService.getCacheUserByKey(accessTokenUser);
            // 校验缓存当中的值是否存在
            validateCache(userCache);
            contextHolder.setUserCache(userCache);
        }
        ThreadLocalCache.setContextHolder(contextHolder);
    }

    /**
     * <b>功能描述：</b>校验token是否为空<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void validateToken (String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusException(ResultEnum.SESSION_VALID.getMsg());
        }
    }

    /**
     * <b>功能描述：</b>校验缓存当中的值是否存在<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private <T> void validateCache(T t) {
        if (t == null) {
            throw new BusException(ResultEnum.SESSION_VALID.getMsg());
        }
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
        // 当请求结束的时候把 ThreadLocal remove，移除不必须要键值对
        ThreadLocalCache.remove();
    }
}
