package com.jzy.api.service.auth.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.dao.auth.SysRoleMapper;
import com.jzy.api.model.auth.Role;
import com.jzy.api.service.auth.SysRoleService;
import com.jzy.api.util.DateUtils;
import com.jzy.api.vo.auth.SysRoleVo;
import com.jzy.framework.bean.vo.PageVo;
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
 * Date : 2019/5/29 17:37
 * Version: V1.0.0
 * Desc: 系统角色管理
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {


	@Resource
	private RedissonClient redissonClient;

	@Resource
	private SysRoleMapper sysRoleMapper;


	@Override
	public PageVo<SysRoleVo> list(SysRoleCnd sysRoleCnd) {
		Page<SysRoleVo> page = PageHelper.startPage(sysRoleCnd.getPage(),sysRoleCnd.getLimit());
		List<SysRoleVo> result = sysRoleMapper.list(sysRoleCnd);
		return new PageVo<>(sysRoleCnd.getPage(), sysRoleCnd.getLimit(), page.getTotal(), result);
	}

	@Override
	public Integer add(SysRoleCnd sysRoleCnd) {
		Role role = Role.build(sysRoleCnd);
		return sysRoleMapper.add(role);
	}

	@Override
	public Role queryById(Long roleId) {
		return sysRoleMapper.queryById(roleId);
	}

	@Override
	public Integer updateById(Role role) {
		return sysRoleMapper.update(role);
	}

	@Override
	public Integer deleteById(Long roleId) {
		return sysRoleMapper.updateDelFLag(roleId);
	}

	@Override
	public List<Role> findByIds(List<Long> roleList) {
		return sysRoleMapper.findByIds(roleList);
	}

	@Override
	public Role findByRoleValue(String roleValue, Long roleId) {
		return sysRoleMapper.findByRoleValue(roleValue,roleId);
	}

	@Override
	public Role findByName(String name) {
		return sysRoleMapper.findByRoleName(name);
	}

	@Override
	public Set<String> findRoleValueByRoleIds(List<Long> roleIds, Long id) {
		Set<String> userRoleValue;
		String key = ApiRedisCacheConstant.USER_ROLE_CACHE + id;

		RBucket<Set<String>> value = redissonClient.getBucket(key);

		if (value.isExists()) {
			userRoleValue = value.get();
		} else {
			List<Role> roles = this.findByIds(roleIds);
			userRoleValue = roles.stream()
								 .filter(Objects::nonNull)
								 .map(Role::getRoleValue)
								 .collect(Collectors.toSet());
			if (CollectionUtils.isNotEmpty(userRoleValue)) {
				value.set(userRoleValue, DateUtils.SECONDS_PER_DAY, TimeUnit.SECONDS);
			}
		}
		return userRoleValue;
	}
}
