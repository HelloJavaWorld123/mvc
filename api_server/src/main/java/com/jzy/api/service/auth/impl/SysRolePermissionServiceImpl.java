package com.jzy.api.service.auth.impl;

import com.jzy.api.dao.auth.SysRolePermissionMapper;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.po.auth.RolePermPo;
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
}
