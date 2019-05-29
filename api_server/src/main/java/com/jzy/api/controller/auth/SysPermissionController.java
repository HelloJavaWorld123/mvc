package com.jzy.api.controller.auth;

import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.result.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : RXK
 * Date : 2019/5/29 19:05
 * Version: V1.0.0
 * Desc: 权限资源管理
 **/
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

	@RequestMapping("/list")
	public ApiResult list(@RequestBody PageCnd pageCnd){

	}

	@RequestMapping("/add")
	public ApiResult add(@RequestBody PageCnd pageCnd){

	}


	@RequestMapping("/update")
	public ApiResult update(@RequestBody PageCnd pageCnd){

	}

	@RequestMapping("/delete")
	public ApiResult delete(@RequestBody PageCnd pageCnd){

	}

	@RequestMapping("/id")
	public ApiResult getById(@RequestBody PageCnd pageCnd){

	}


}
