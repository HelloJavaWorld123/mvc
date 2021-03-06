package com.jzy.api.base;

import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author : RXK
 * Date : 2019/6/1 19:05
 * Version: V1.0.0
 * Desc:
 **/
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalException {


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResult handleParamVerifyException(MethodArgumentNotValidException e){
		String message = e.getBindingResult()
								 .getFieldError()
								 .getDefaultMessage();
		return new ApiResult().fail(message,ResultEnum.FAIL.getCode());
	}

	@ExceptionHandler(value = {UnauthorizedException.class})
	public ApiResult handleUnauthorizedException(UnauthorizedException e){
		log.info("授权失败异常:",e);
		return new ApiResult().fail(ResultEnum.PERMISSION_DENIED);
	}

	@ExceptionHandler(value = {LockedAccountException.class})
	public ApiResult handleLockedAccountException(LockedAccountException e){
		log.info("账号异常:",e);
		return new ApiResult().fail("用户账号或者秘密错误",ResultEnum.PERMISSION_DENIED.getCode());
	}

	@ExceptionHandler(value = {UnknownAccountException.class})
	public ApiResult handleUnknownAccountException(UnknownAccountException e){
		log.info("账号异常:",e);
		return new ApiResult().fail("用户账号错误",ResultEnum.PERMISSION_DENIED.getCode());
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ApiResult handleAssertException(IllegalArgumentException e){
		String message = e.getCause()
						  .getCause()
						  .getMessage();
		return new ApiResult().fail(message,ResultEnum.FAIL.getCode());
	}


	@ExceptionHandler(value = {IncorrectCredentialsException.class})
	public ApiResult handleIncorrectCredentialsException(IncorrectCredentialsException e){
		log.info("密码错误异常：",e);
		return new ApiResult().fail("密码错误",ResultEnum.PERMISSION_DENIED.getCode());
	}

	@ExceptionHandler(value = {AuthorizationException.class})
	public ApiResult handleAuthorizationException(AuthorizationException e){
		log.info("账号授权异常:",e);
		return new ApiResult().fail(ResultEnum.PERMISSION_DENIED);
	}



}
