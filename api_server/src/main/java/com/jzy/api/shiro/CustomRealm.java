package com.jzy.api.shiro;

import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.service.auth.*;
import com.jzy.api.util.DateUtils;
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
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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

	@Resource
	private RedissonClient redissonClient;

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

		List<Long> roleIds = getSysEmpRoles(emp);
		if (CollectionUtils.isEmpty(roleIds)) {
			log.info("用户：{}没有授予角色", emp.getId());
			throw new UnauthorizedException(ResultEnum.USER_ACCOUNT_UNAUTHORIZED_ERROR.getMsg());
		}

		Set<String> sysRolePermissions = getSysRolePermissions(roleIds, emp);
		if (CollectionUtils.isEmpty(sysRolePermissions)) {
			log.info("用户：{} 暂时没有授予权限", emp.getId());
			throw new UnauthorizedException(ResultEnum.USER_ACCOUNT_UNAUTHORIZED_ERROR.getMsg());
		}

		Set<String> roleValues = getRoleValue(roleIds, emp);
		emp.setRoleValues(roleValues);
		emp.setPermValues(sysRolePermissions);

		return new SimpleAuthenticationInfo(emp, emp.getPassword(), getName());
	}


	private Set<String> getRoleValue(List<Long> roleIds, SysEmp emp) {
		Set<String> userRoleValue = null;
		String key = ApiRedisCacheConstant.USER_ROLE_CACHE + emp.getId();

		RBucket<Set<String>> value = redissonClient.getBucket(key);

		if (value.isExists()) {
			userRoleValue = value.get();
		} else {
			List<Role> roles = sysRoleService.findByIds(roleIds);
			if (CollectionUtils.isNotEmpty(roles)) {
				userRoleValue = roles.stream()
									 .map(Role::getRoleValue)
									 .collect(Collectors.toSet());
				value.set(userRoleValue, DateUtils.SECONDS_PER_DAY, TimeUnit.SECONDS);
			}
		}
		return userRoleValue;

	}


	private Set<String> getSysRolePermissions(List<Long> roleIds, SysEmp emp) {
		Set<String> permissionValue = null;
		String key = ApiRedisCacheConstant.USER_PERMISSION_CACHE + emp.getId();
		RBucket<Set<String>> value = redissonClient.getBucket(key);
		if (value.isExists()) {
			permissionValue = value.get();
		} else {
			List<SysRolePermission> rolePermissions = sysRolePermissionService.findByRoleIds(roleIds);
			if (CollectionUtils.isNotEmpty(rolePermissions)) {
				permissionValue = rolePermissions.stream()
												 .filter(Objects::nonNull)
												 .map(SysRolePermission::getPermissionKey)
												 .collect(Collectors.toSet());

				value.set(permissionValue, DateUtils.SECONDS_PER_DAY, TimeUnit.SECONDS);
			}
		}
		return permissionValue;
	}

	private List<Long> getSysEmpRoles(SysEmp sysEmp) {
		List<Long> roleIds = null;
		String key = ApiRedisCacheConstant.USER_ROLE_IDS_CACHE + sysEmp.getId();
		RBucket<List<Long>> bucket = redissonClient.getBucket(key);
		if (bucket.isExists()) {
			roleIds = bucket.get();
		} else {
			List<SysEmpRole> roles = sysEmpRoleService.findByEmpId(sysEmp.getId());
			if (CollectionUtils.isNotEmpty(roles)) {
				roleIds = roles.stream()
							   .filter(Objects::nonNull)
							   .map(SysEmpRole::getRoleId)
							   .collect(Collectors.toList());
				bucket.set(roleIds,DateUtils.SECONDS_PER_DAY,TimeUnit.SECONDS);
			}
		}

		return roleIds;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		super.setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	@Override
	protected boolean isAuthenticationCachingEnabled(AuthenticationToken token, AuthenticationInfo info) {
		return Boolean.TRUE;
	}

	@Override
	public boolean isCachingEnabled() {
		return Boolean.TRUE;
	}


	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if(null != cache){
			cache.clear();
		}
	}

	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		Cache<Object, AuthenticationInfo> cache = getAuthenticationCache();
		if(null != cache){
			cache.clear();
		}
	}
}
