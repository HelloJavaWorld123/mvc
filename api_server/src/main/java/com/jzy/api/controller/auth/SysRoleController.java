package com.jzy.api.controller.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.IDValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.model.auth.*;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author : RXK
 * Date : 2019/5/29 17:33
 * Version: V1.0.0
 * Desc: 系统角色管理
 **/
@RestController
@RequestMapping("/sys/role")
@RequiresRoles(value = {"root"})
@Api(tags = "后端-权限")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	@Resource
	private RedissonClient redissonClient;

	@Autowired
	private SysEmpRoleService sysEmpRoleService;

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	@RequestMapping("/list")
	@ApiOperation(httpMethod="POST" ,value = "角色列表")
	public ApiResult list(@RequestBody @Validated(value = {PageCnd.PageValidator.class}) SysRoleCnd sysRoleCnd) {
		PageVo<SysRoleVo> sysRoleVos = sysRoleService.list(sysRoleCnd);
		return new ApiResult<>(sysRoleVos);
	}


	@RequestMapping("/add")
	@ApiOperation(httpMethod="POST" ,value = "保存角色")
	public ApiResult add(@RequestBody @Validated(value = {CreateValidator.class}) SysRoleCnd sysRoleCnd) {

		verifyRoleValue(sysRoleCnd, null);

		sysRoleCnd.setOperatorId(getOperatorId());

		Integer result = sysRoleService.add(sysRoleCnd);
		return getResultEnum(result);
	}


	@RequestMapping("/update")
	@ApiOperation(httpMethod="POST" ,value = "更新角色")
	public ApiResult update(@RequestBody @Validated(value = {UpdateValidator.class}) SysRoleCnd sysRoleCnd) {
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		if (Objects.isNull(role)) {
			return getResultEnum(0);
		}

		verifyRoleValue(sysRoleCnd, sysRoleCnd.getId());

		sysRoleCnd.setOperatorId(getOperatorId());

		Role update = Role.update(sysRoleCnd);
		Integer result = sysRoleService.updateById(update);
		return getResultEnum(result);
	}


	@RequestMapping("/delete")
	@ApiOperation(httpMethod="POST" ,value = "删除角色")
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

	@RequestMapping("/id")
	@ApiOperation(httpMethod="POST" ,value = "角色详情")
	public ApiResult getById(@RequestBody @Validated(IDValidator.class) SysRoleCnd sysRoleCnd) {
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		return new ApiResult<>().success(role);
	}

	@RequestMapping("/perm")
	@ApiOperation(httpMethod="POST" ,value = "角色权限列表")
	public ApiResult getPermByRoleId(@RequestBody @Validated(IDValidator.class) SysRoleCnd sysRoleCnd) {
		List<SysRolePermission> rolePermissions = sysRolePermissionService.findByRoleId(sysRoleCnd.getId());
		return new ApiResult<>().success(rolePermissions);
	}


	@RequestMapping("/allot/perm")
	@ApiOperation(httpMethod="POST" ,value = "分配权限")
	public ApiResult allotPermission(@RequestBody @Validated(SysRoleCnd.AllotPermValidator.class) SysRoleCnd sysRoleCnd) {
		Role role = sysRoleService.queryById(sysRoleCnd.getId());
		Assert.isTrue(Objects.nonNull(role), "角色参数错误");

		List<RolePermPo> rolePermPos = null;
		if (CollectionUtils.isNotEmpty(sysRoleCnd.getPermIds())) {
			List<SysPermission> permValues = getPermValues(sysRoleCnd.getPermIds());
			rolePermPos = RolePermPo.build(sysRoleCnd.getId(), permValues);
		}

		sysRolePermissionService.deleteByRoleIdAndPermType(sysRoleCnd.getId(), sysRoleCnd.getPermType());
		deleteCache(sysRoleCnd.getId());

		if (CollectionUtils.isNotEmpty(rolePermPos)) {
			Integer result = sysRolePermissionService.add(rolePermPos);
			return result >= 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);
		}


		return getResultEnum(1);

	}

	private void deleteCache(Long id) {
		List<SysEmpRole> sysEmpRoles = sysEmpRoleService.findByRoleId(id);
		if(CollectionUtils.isNotEmpty(sysEmpRoles)){
			sysEmpRoles.forEach(item ->{
				String key = ApiRedisCacheConstant.USER_PERMISSION_CACHE + item.getEmpId();
				redissonClient.getBucket(key)
							  .deleteAsync();
			});
		}
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

	private Long getOperatorId(){
		SysEmp sysEmp = (SysEmp) SecurityUtils.getSubject()
											  .getPrincipal();
		return sysEmp.getId();
	}

}
