//package com.jzy.api.base;
//
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authz.UnauthorizedException;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.util.StringUtils;
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Author : RXK
// * Date : 2019/6/4 17:49
// * Version: V1.0.0
// * Desc:
// **/
//@Configuration
//public class CustomerAccessControlFilter extends AccessControlFilter {
//
//
//	@Override
//	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//		return SecurityUtils.getSubject()
//							.isAuthenticated();
//	}
//
//	@Override
//	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//		saveRequestAndRedirectToLogin(request, response);
//		return false;
//	}
//
//	@Override
//	protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
//		throw new UnauthorizedException("没有权限");
//	}
//}
