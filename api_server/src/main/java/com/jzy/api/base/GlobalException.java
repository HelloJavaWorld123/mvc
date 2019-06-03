package com.jzy.api.base;

import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
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

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ApiResult handleAssertException(IllegalArgumentException e){
		String message = e.getCause()
						  .getCause()
						  .getMessage();
		return new ApiResult().fail(message,ResultEnum.FAIL.getCode());
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ApiResult handleUnAuthorizedException(UnauthorizedException e){
		log.info("无权限异常：",e);
		return new ApiResult().fail("无权限");
	}


	@ExceptionHandler(Exception.class)
	public ApiResult handleException(Exception e){
		log.info("捕捉到的异常：",e);
		return new ApiResult().fail(e.getMessage());
	}


}
