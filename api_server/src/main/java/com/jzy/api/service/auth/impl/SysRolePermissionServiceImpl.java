package com.jzy.api.service.auth.impl;

import com.jzy.api.dao.auth.SysRolePermissionMapper;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.service.auth.SysRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Author : RXK
 * Date : 2019/5/30 15:28
 * Version: V1.0.0
 * Desc:
 **/
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {

	@Resource
	private SysRolePermissionMapper sysRolePermissionMapper;

	@Override
	public Integer add(Long id, List<String> permValues) {
		sysRolePermissionMapper.deleteByRoleId(id);
		return sysRolePermissionMapper.add(id, permValues);
	}

	@Override
	public List<SysRolePermission> findByRoleId(Long roleId) {
		return sysRolePermissionMapper.findByRoleId(roleId);
	}

	@Override
	public List<SysRolePermission> findByRoleIds(List<Long> roleIds) {
		return sysRolePermissionMapper.findByRoleIds(roleIds);
	}
}
