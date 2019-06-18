package com.jzy.api.controller.auth;

import com.jzy.api.annos.*;
import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.service.auth.SysPermissionService;
import com.jzy.api.vo.auth.SysPermissionVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author : RXK
 * Date : 2019/5/29 19:05
 * Version: V1.0.0
 * Desc: 权限资源管理
 **/
@RestController
@RequestMapping("/sys/permission")
@RequiresRoles(value = {"root"})
@Api(tags = "后端-权限")
public class SysPermissionController {

	@Autowired
	private SysPermissionService sysPermissionService;

	@RequestMapping("/list")
	@ApiOperation(httpMethod="POST" ,value = "权限列表")
	public ApiResult list(@RequestBody SysPermissionCnd permissionCnd) {
		List<SysPermissionVo> pageList = sysPermissionService.list(permissionCnd);
		return new ApiResult<>().success(pageList);
	}

	@RequestMapping("/add")
	@ApiOperation(httpMethod="POST" ,value = "添加权限")
	public ApiResult add(@RequestBody @Validated(value = {CreateValidator.class}) SysPermissionCnd permissionCnd) {
		isLeafNode(permissionCnd);

		permissionCnd.setOperatorId(getOperatorId());

		SysPermission permission = SysPermission.build(permissionCnd);
		Integer result = sysPermissionService.add(permission);
		return result >= 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult<>().fail(ResultEnum.FAIL);
	}


	@RequestMapping("/update")
	@ApiOperation(httpMethod="POST" ,value = "更新权限")
	public ApiResult update(@RequestBody @Validated(value = {UpdateValidator.class}) SysPermissionCnd permissionCnd) {
		SysPermission serviceById = sysPermissionService.findById(permissionCnd.getId());
		if (Objects.isNull(serviceById)) {
			return result(NumberUtils.INTEGER_ZERO);
		}

		verifyPermissionValue(permissionCnd);

		isLeafNode(permissionCnd);

		permissionCnd.setOperatorId(getOperatorId());

		SysPermission permission = SysPermission.update(permissionCnd);
		Integer result = sysPermissionService.update(permission);
		return result(result);
	}



	@RequestMapping("/delete")
	@ApiOperation(httpMethod="POST" ,value = "删除权限")
	public ApiResult delete(@RequestBody @Validated(value = {DeleteValidator.class}) SysPermissionCnd permissionCnd) {
		SysPermission sysPermission = sysPermissionService.findById(permissionCnd.getId());
		if (Objects.isNull(sysPermission)) {
			return result(NumberUtils.INTEGER_ZERO);
		}
		Integer result = sysPermissionService.delete(permissionCnd.getId());
		return result(result);
	}

	@RequestMapping("/id")
	@ApiOperation(httpMethod="POST" ,value = "查询权限")
	public ApiResult getById(@RequestBody @Validated(IDValidator.class) SysPermissionCnd permissionCnd) {
		SysPermission permission = sysPermissionService.findById(permissionCnd.getId());
		SysPermissionVo vo = new SysPermissionVo();
		BeanUtils.copyProperties(permission, vo);
		return new ApiResult<>().success(vo);
	}

	private void isLeafNode(@Validated({CreateValidator.class}) @RequestBody SysPermissionCnd permissionCnd) {
		if (StringUtils.isNotBlank(permissionCnd.getParentKey())) {
			permissionCnd.setLeafNode(NumberUtils.INTEGER_ONE);
		} else {
			permissionCnd.setLeafNode(NumberUtils.INTEGER_ZERO);
		}
	}


	private void verifyPermissionValue(SysPermissionCnd permissionCnd) {
		SysPermission sysPermission = sysPermissionService.findByUniqueKey(permissionCnd.getUniqueKey());
		if (Objects.nonNull(sysPermission) && sysPermission.getId().compareTo(permissionCnd.getId()) != 0) {
			Assert.isTrue(StringUtils.isBlank(permissionCnd.getParentKey()) && StringUtils.isNotBlank(sysPermission.getParentKey())
								  || StringUtils.isNotBlank(permissionCnd.getParentKey()) && StringUtils.isBlank(sysPermission.getParentKey())
								  || StringUtils.isNotBlank(permissionCnd.getParentKey()) && StringUtils.isNotBlank(sysPermission.getParentKey()) && !permissionCnd.getParentKey()
																																								   .equalsIgnoreCase(sysPermission.getParentKey()), "同级别下不能存在相同的key");
		}
	}


	private ApiResult result(int result) {
		return result == 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);
	}

	private Long getOperatorId() {
		SysEmp sysEmp = (SysEmp) SecurityUtils.getSubject()
											  .getPrincipal();
		return sysEmp.getId();
	}



}
