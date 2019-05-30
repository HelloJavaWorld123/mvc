package com.jzy.api.service.auth.impl;

import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.api.dao.auth.SysPermissionMapper;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.service.auth.SysPermissionService;
import com.jzy.api.vo.auth.SysPermissionVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 10:09
 * Version: V1.0.0
 * Desc:
 **/
@Service
public class SysPermissionServiceImpl implements SysPermissionService {


	@Resource
	private SysPermissionMapper sysPermissionMapper;

	@Override
	public List<SysPermissionVo> list(SysPermissionCnd permissionCnd) {
		return sysPermissionMapper.list(permissionCnd);
	}

	@Override
	public Integer add(SysPermission permission) {
		return sysPermissionMapper.add(permission);
	}

	@Override
	public Integer update(SysPermission permission) {
		return sysPermissionMapper.update(permission);
	}

	@Override
	public Integer delete(Long permissionId) {
		return sysPermissionMapper.delete(permissionId);
	}

	@Override
	public SysPermission findById(Long id) {
		return sysPermissionMapper.findById(id);
	}

	@Override
	public List<SysPermission> findByKeys(List<String> permValues) {
		return sysPermissionMapper.findByUniqueKeys(permValues);
	}
}
