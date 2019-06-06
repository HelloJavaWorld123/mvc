package com.jzy.api.controller.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.IDValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.po.auth.RolePermPo;
import com.jzy.api.service.auth.SysEmpRoleService;
import com.jzy.api.service.auth.SysPermissionService;
import com.jzy.api.service.auth.SysRolePermissionService;
import com.jzy.api.service.auth.SysRoleService;
import com.jzy.api.vo.auth.SysRoleVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.result.ApiResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Author : RXK
 * Date : 2019/5/29 17:33
 * Version: V1.0.0
 * Desc: 系统角色管理
 **/
@RestController
@RequestMapping("/sys/role")
@RequiresRoles(value = {"admin"})
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysEmpRoleService sysEmpRoleService;

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	@RequestMapping("/list")
	public ApiResult list(@RequestBody @Validated(value = {PageCnd.PageValidator.class}) SysRoleCnd sysRoleCnd) {
		PageVo<SysRoleVo> sysRoleVos = sysRoleService.list(sysRoleCnd);
		return new ApiResult<>(sysRoleVos);
	}


	@RequestMapping("/add")
	public ApiResult add(@RequestBody @Validated(value = {CreateValidator.class}) SysRoleCnd sysRoleCnd) {

		verifyRoleValue(sysRoleCnd, null);

		Integer result = sysRoleService.add(sysRoleCnd);
		return getResultEnum(result);
	}


	@RequestMapping("/update")
	public ApiResult update(@RequestBody @Validated(value = {UpdateValidator.class}) SysRoleCnd sysRoleCnd) {
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		if (Objects.isNull(role)) {
			return getResultEnum(0);
		}

		verifyRoleValue(sysRoleCnd, sysRoleCnd.getId());

		Role update = Role.update(sysRoleCnd);
		Integer result = sysRoleService.updateById(update);
		return getResultEnum(result);
	}


	@RequestMapping("/delete")
	public ApiResult delete(@RequestBody @Validated(value = {DeleteValidator.class}) SysRoleCnd sysRoleCnd) {
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		if (null == role) {
			return getResultEnum(0);
		}

		List<SysEmpRole> sysEmpRoles = sysEmpRoleService.findByRoleId(sysRoleCnd.getId());
		if (CollectionUtils.isNotEmpty(sysEmpRoles)) {
			return new ApiResult().fail("该角色有用户在使用,请确认", ResultEnum.FAIL.getCode());
		}

		Integer result = sysRoleService.deleteById(role.getId());
		return getResultEnum(result);
	}


	@RequestMapping("/check")
	public ApiResult roleValueCheck(@RequestBody @Validated(SysRoleCnd.RoleValueExist.class) SysRoleCnd sysRoleCnd) {
		verifyRoleValue(sysRoleCnd, sysRoleCnd.getId());
		return new ApiResult().success();
	}


	@RequestMapping("/id")
	public ApiResult getById(@RequestBody @Validated(IDValidator.class) SysRoleCnd sysRoleCnd) {
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		return new ApiResult<>().success(role);
	}

	@RequestMapping("/perm")
	public ApiResult getPermByRoleId(@RequestBody @Validated(IDValidator.class) SysRoleCnd sysRoleCnd) {
		List<SysRolePermission> rolePermissions = sysRolePermissionService.findByRoleId(sysRoleCnd.getId());
		return new ApiResult<>().success(rolePermissions);
	}


	@RequestMapping("/allot/perm")
	public ApiResult allotPermission(@RequestBody @Validated(SysRoleCnd.Allot.class) SysRoleCnd sysRoleCnd) {
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		Assert.isTrue(Objects.nonNull(role), "角色参数错误");

		List<SysPermission> permValues = getPermValues(sysRoleCnd.getPermIds());
		List<RolePermPo> rolePermPos = RolePermPo.build(sysRoleCnd.getId(), permValues);

		sysRolePermissionService.deleteByRoleIdAndPermType(sysRoleCnd.getId(),sysRoleCnd.getPermType());
		Integer result = sysRolePermissionService.add(rolePermPos);
		return result >= 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);

	}

	private List<SysPermission> getPermValues(List<Long> permIds) {
		List<SysPermission> sysPermissionList = sysPermissionService.findByIds(permIds);
		Assert.isTrue(CollectionUtils.isNotEmpty(sysPermissionList) && sysPermissionList.size() == permIds.size(), "角色参数有误");
		return sysPermissionList;
	}


	private ApiResult getResultEnum(Integer result) {
		return result == 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);
	}

	private void verifyRoleValue(@Validated({UpdateValidator.class}) @RequestBody SysRoleCnd sysRoleCnd, Long id) {
		Role value = sysRoleService.findByRoleValue(sysRoleCnd.getRoleValue(), id);
		Assert.isTrue(Objects.isNull(value), "角色值已经存在");
	}

}
