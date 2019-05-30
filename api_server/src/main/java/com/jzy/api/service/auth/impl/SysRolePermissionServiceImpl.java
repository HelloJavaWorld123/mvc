package com.jzy.api.service.auth.impl;

import com.jzy.api.dao.auth.SysRolePermissionMapper;
import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.service.auth.SysRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
	public Integer add(Long id, List<String> permValues, Integer permType) {
		sysRolePermissionMapper.deleteByRoleId(id);
		return sysRolePermissionMapper.add(id, permValues,permType);
	}

	@Override
	public List<SysRolePermission> findByRoleId(Long roleId) {
		return sysRolePermissionMapper.findByRoleId(roleId);
	}
}
