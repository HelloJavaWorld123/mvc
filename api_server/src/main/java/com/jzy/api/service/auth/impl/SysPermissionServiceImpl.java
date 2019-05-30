package com.jzy.api.service.auth.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.api.dao.auth.SysPermissionMapper;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.service.auth.SysPermissionService;
import com.jzy.api.vo.auth.SysPermissionVo;
import com.jzy.framework.bean.vo.PageVo;
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
	public PageVo<SysPermissionVo> list(SysPermissionCnd permissionCnd) {
		Page<SysPermissionVo> page = PageHelper.startPage(permissionCnd.getPage(), permissionCnd.getLimit());
		List<SysPermissionVo> list = sysPermissionMapper.list(permissionCnd);
		return new PageVo<>(permissionCnd.getPage(), permissionCnd.getLimit(), page.getTotal(), list);
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
}
