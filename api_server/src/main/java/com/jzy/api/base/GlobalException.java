package com.jzy.api.base;

import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
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
@ControllerAdvice
@ResponseBody
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


}
