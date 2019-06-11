package com.jzy.api.shiro;

import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.service.auth.SysEmpRoleService;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.service.auth.SysRolePermissionService;
import com.jzy.api.service.auth.SysRoleService;
import com.jzy.common.enums.ResultEnum;
import com.jzy.common.enums.UserAccountStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Author : RXK
 * Date : 2019/5/31 14:31
 * Version: V1.0.0
 * Desc: 用户授权 以及 权限资源获取
 **/
@Slf4j
@Service
public class CustomAuthorizingRealm extends AuthorizingRealm {

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

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(sysEmp.getPermValues());
		simpleAuthorizationInfo.setRoles(sysEmp.getRoleValues());

		return simpleAuthorizationInfo;
	}


	/**
	 * 用户登录 验证授权
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();
		List<SysEmp> sysEmp = sysEmpService.findByName(username, null);

		if (CollectionUtils.isEmpty(sysEmp)) {
			log.info("暂无相关用户信息:{}", username);
			throw new UnknownAccountException(ResultEnum.USER_ACCOUNT_ERROR.getMsg());
		}
		SysEmp emp = sysEmp.get(0);

		if (emp.getStatus() == UserAccountStatusEnum.STOP_STATUS.getStatus()) {
			log.info("当前用户状态已关闭：{}", emp.getId());
			throw new LockedAccountException(ResultEnum.USER_ACCOUNT_STATUS_ERROR.getMsg());
		}

		List<Long> roleIds =  sysEmpRoleService.findRoleIdsByEmpId(emp.getId());
		if (CollectionUtils.isEmpty(roleIds)) {
			log.info("用户：{}没有授予角色", emp.getId());
			throw new UnauthorizedException(ResultEnum.USER_ACCOUNT_UNAUTHORIZED_ERROR.getMsg());
		}

		Set<String> sysRolePermissions = sysRolePermissionService.findByPermissionKeyByRoleIds(roleIds,emp.getId());
		if (CollectionUtils.isEmpty(sysRolePermissions)) {
			log.info("用户：{} 暂时没有授予权限", emp.getId());
			throw new UnauthorizedException(ResultEnum.USER_ACCOUNT_UNAUTHORIZED_ERROR.getMsg());
		}

		Set<String> roleValues = sysRoleService.findRoleValueByRoleIds(roleIds,emp.getId());
		emp.setRoleValues(roleValues);
		emp.setPermValues(sysRolePermissions);

		return new SimpleAuthenticationInfo(emp, emp.getPassword(), getName());
	}


	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		super.setCredentialsMatcher(new CustomCredentialsMatcher());
	}


	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (null != cache) {
			cache.clear();
		}
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		Cache<Object, AuthenticationInfo> cache = getAuthenticationCache();
		if (null != cache) {
			cache.clear();
		}
	}
}
