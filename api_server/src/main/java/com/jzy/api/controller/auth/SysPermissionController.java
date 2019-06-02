package com.jzy.api.controller.auth;

import com.jzy.api.annos.*;
import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.service.auth.SysPermissionService;
import com.jzy.api.vo.auth.SysPermissionVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Author : RXK
 * Date : 2019/5/29 19:05
 * Version: V1.0.0
 * Desc: 权限资源管理
 **/
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;

	@RequestMapping("/list")
	public ApiResult list(@RequestBody SysPermissionCnd permissionCnd){
		List<SysPermissionVo> pageList = sysPermissionService.list(permissionCnd);
		return new ApiResult<>().success(pageList);
	}

	@RequestMapping("/add")
	public ApiResult add(@RequestBody @Validated(value = {CreateValidator.class}) SysPermissionCnd permissionCnd){
		SysPermission permission = SysPermission.build(permissionCnd);
		Integer result = sysPermissionService.add(permission);
		return result == 1 ? new ApiResult().success() : new ApiResult().fail(ResultEnum.FAIL);
	}


	@RequestMapping("/update")
	public ApiResult update(@RequestBody @Validated(value = {UpdateValidator.class}) SysPermissionCnd permissionCnd){
		SysPermission serviceById = sysPermissionService.findById(permissionCnd.getId());
		if(Objects.isNull(serviceById)){
			return new ApiResult().fail(ResultEnum.FAIL);
		}
		SysPermission permission = SysPermission.update(permissionCnd);
		Integer result = sysPermissionService.update(permission);
		ApiResult apiResult = new ApiResult();
		return result == 1 ? apiResult.success() : apiResult.fail(ResultEnum.FAIL);
	}

	@RequestMapping("/delete")
	public ApiResult delete(@RequestBody @Validated(value = {DeleteValidator.class}) SysPermissionCnd permissionCnd){
		SysPermission sysPermission= sysPermissionService.findById(permissionCnd.getId());
		if(Objects.isNull(sysPermission)){
			return new ApiResult().fail(ResultEnum.FAIL);
		}
		Integer result = sysPermissionService.delete(permissionCnd.getId());
		ApiResult apiResult = new ApiResult();
		return result == 1 ? apiResult.success() : apiResult.fail(ResultEnum.FAIL);
	}

	@RequestMapping("/id")
	public ApiResult getById(@RequestBody @Validated(IDValidator.class) SysPermissionCnd permissionCnd){
		SysPermission permission = sysPermissionService.findById(permissionCnd.getId());
		SysPermissionVo vo = new SysPermissionVo();
		BeanUtils.copyProperties(permission,vo);
		return new ApiResult<>().success(vo);
	}


}
