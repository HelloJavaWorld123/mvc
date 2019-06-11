package com.jzy.api.service.auth.impl;

import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.dao.auth.SysRolePermissionMapper;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.po.auth.RolePermPo;
import com.jzy.api.service.auth.SysRolePermissionService;
import com.jzy.api.util.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Author : RXK
 * Date : 2019/5/30 15:28
 * Version: V1.0.0
 * Desc:
 **/
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {


	@Resource
	private RedissonClient redissonClient;

	@Resource
	private SysRolePermissionMapper sysRolePermissionMapper;

	@Override
	public List<SysRolePermission> findByRoleId(Long roleId) {
		return sysRolePermissionMapper.findByRoleId(roleId);
	}

	@Override
	public List<SysRolePermission> findByRoleIds(List<Long> roleIds) {
		return sysRolePermissionMapper.findByRoleIds(roleIds);
	}

	@Override
	public Integer add(List<RolePermPo> rolePermPos) {
		return sysRolePermissionMapper.batchAdd(rolePermPos);
	}

	@Override
	public void deleteByRoleIdAndPermType(Long roleId, Integer permType) {
		sysRolePermissionMapper.deleteByRoleId(roleId,permType);
	}

	@Override
	public Set<String> findByPermissionKeyByRoleIds(List<Long> roleIds, Long id) {
		Set<String> permissionValue = null;
		String key = ApiRedisCacheConstant.USER_PERMISSION_CACHE + id;
		RBucket<Set<String>> value = redissonClient.getBucket(key);
		if (value.isExists()) {
			permissionValue = value.get();
		} else {
			List<SysRolePermission> rolePermissions = this.findByRoleIds(roleIds);
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
}
