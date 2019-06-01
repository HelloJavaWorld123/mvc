package com.jzy.api.controller.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.IDValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.service.auth.SysPermissionService;
import com.jzy.api.service.auth.SysRolePermissionService;
import com.jzy.api.service.auth.SysRoleService;
import com.jzy.api.vo.auth.SysRoleVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysRoleController extends GenericController {

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	@RequestMapping("/list")
	public ApiResult list(@RequestBody @Validated(value = {PageCnd.PageValidator.class}) SysRoleCnd sysRoleCnd){
		PageVo<SysRoleVo> sysRoleVos = sysRoleService.list(sysRoleCnd);
		return new ApiResult<>(sysRoleVos);
	}


	@RequestMapping("/add")
	private ApiResult add(@RequestBody @Validated(value = {CreateValidator.class}) SysRoleCnd sysRoleCnd){
		Integer result = sysRoleService.add(sysRoleCnd);
		return new ApiResult<>(getResultEnum(result));
	}


	@RequestMapping("/update")
	public ApiResult update(@RequestBody @Validated(value = {UpdateValidator.class}) SysRoleCnd sysRoleCnd){
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		if(Objects.isNull(role)){
			return new ApiResult<>(ResultEnum.FAIL);
		}
		Role update = Role.update(sysRoleCnd);
		Integer result = sysRoleService.updateById(update);
		return new ApiResult<>(getResultEnum(result));
	}



	@RequestMapping("/delete")
	public ApiResult delete(@RequestBody @Validated(value = {DeleteValidator.class}) SysRoleCnd sysRoleCnd){
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		if(null == role){
			return new ApiResult<>(ResultEnum.FAIL);
		}

		Integer result = sysRoleService.deleteById(role);
		return new ApiResult<>(getResultEnum(result));
	}


	@RequestMapping("/id")
	public ApiResult getById(@RequestBody @Validated(IDValidator.class) SysRoleCnd sysRoleCnd){
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		return new ApiResult<>(role);
	}

	private ResultEnum getResultEnum(Integer result) {
		return result == 1 ? ResultEnum.SUCCESS : ResultEnum.FAIL;
	}

	@RequestMapping("/allot/perm")
	public ApiResult allotPermission(@RequestBody @Validated(SysRoleCnd.Allot.class) SysRoleCnd sysRoleCnd){
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		if(Objects.isNull(role)){
			return new ApiResult().fail(ResultEnum.FAIL);
		}

		List<SysPermission> permissions = sysPermissionService.findByKeys(sysRoleCnd.getPermValues());
		if (CollectionUtils.isEmpty(permissions) && permissions.size()!= sysRoleCnd.getPermValues().size()) {
			return new ApiResult().fail(ResultEnum.FAIL);
		}

		Integer result = sysRolePermissionService.add(sysRoleCnd.getId(),sysRoleCnd.getPermValues(),sysRoleCnd.getPermType());
		return result >= 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);

	}





}
