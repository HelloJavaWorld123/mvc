package com.jzy.api.service.auth;

import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.common.enums.ResultEnum;
import com.jzy.common.enums.UserAccountStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author : RXK
 * Date : 2019/5/31 14:31
 * Version: V1.0.0
 * Desc: 用户授权 以及 权限资源获取
 **/
@Slf4j
@Service
public class CustomRealm extends AuthorizingRealm {

	@Autowired
	private SysEmpService sysEmpService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysEmpRoleService sysEmpRoleService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;


	/**
	 * 用户权限授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysEmp sysEmp = (SysEmp) getAvailablePrincipal(principals);

		List<Long> roleIds = getSysEmpRoles(sysEmp);
//		if (CollectionUtils.isEmpty(roleIds)) {
//			log.info("用户：{}没有授予角色", sysEmp.getId());
//			throw new UnauthorizedException(ResultEnum.USER_ACCOUNT_UNAUTHORIZED_ERROR.getMsg());
//		}

		Set<String> sysRolePermissions = getSysRolePermissions(roleIds);
//		if (CollectionUtils.isEmpty(sysRolePermissions)) {
//			log.info("用户：{} 暂时没有授予权限", sysEmp.getId());
//			throw new UnauthorizedException(ResultEnum.USER_ACCOUNT_UNAUTHORIZED_ERROR.getMsg());
//		}

		Set<String> roleValues = getRoleName(roleIds);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setRoles(roleValues);
		simpleAuthorizationInfo.setStringPermissions(sysRolePermissions);
		sysEmp.setRoleValues(roleValues);
		sysEmp.setPermValues(sysRolePermissions);

		return simpleAuthorizationInfo;
	}


	/**
	 * 用户登录 验证授权
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		List<SysEmp> sysEmp = sysEmpService.findByName(username);

		if (CollectionUtils.isEmpty(sysEmp)) {
			log.info("暂无相关用户信息:{}", username);
			throw new UnknownAccountException(ResultEnum.USER_ACCOUNT_ERROR.getMsg());
		}
		SysEmp emp = sysEmp.get(0);

		if (emp.getStatus() == UserAccountStatusEnum.STOP_STATUS.getStatus()) {
			log.info("当前用户状态已关闭：{}", emp.getId());
			throw new LockedAccountException(ResultEnum.USER_ACCOUNT_STATUS_ERROR.getMsg());
		}

		return new SimpleAuthenticationInfo(sysEmp, emp.getPassword(), getName());
	}


	private Set<String> getRoleName(List<Long> roleIds) {
		List<Role> roles = sysRoleService.findByIds(roleIds);
		return roles.stream()
					.map(Role::getRoleValue)
					.collect(Collectors.toSet());
	}


	private Set<String> getSysRolePermissions(List<Long> roleIds) {
		List<SysRolePermission> rolePermissions = sysRolePermissionService.findByRoleIds(roleIds);
		return rolePermissions.stream().filter(Objects::nonNull)
							  .map(SysRolePermission::getPermissionKey)
							  .collect(Collectors.toSet());
	}

	private List<Long> getSysEmpRoles(SysEmp sysEmp) {
		List<SysEmpRole> roles = sysEmpRoleService.findByEmpId(sysEmp.getId());
		return roles.stream()
					.filter(Objects::nonNull)
					.map(SysEmpRole::getRoleId)
					.collect(Collectors.toList());
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		super.setCredentialsMatcher(new CustomCredentialsMatcher());
	}


	@Override
	protected void doClearCache(PrincipalCollection principals) {
		super.doClearCache(principals);
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}
}
