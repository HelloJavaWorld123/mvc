package com.jzy.api.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by IntelliJ IDEA. @Date 2019-04-17
 * 跨域过滤器
 * @Author 刘宏超
 */
@Component
public class CrossFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException,
            IOException {

        HttpServletResponse response = (HttpServletResponse) res;
        //response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, appType, apiEmpToken, apiUserToken");
        response.setHeader("Access-Control-Allow-Credentials", "true"); //是否支持cookie跨域
        chain.doFilter(req, res);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
