package com.jzy.api.service.auth.impl;

import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.dao.auth.SysEmpRoleMapper;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.service.auth.SysEmpRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 14:39
 * Version: V1.0.0
 * Desc:
 **/
@Service
public class SysEmpRoleServiceImpl implements SysEmpRoleService {

	@Resource
	private SysEmpRoleMapper sysEmpRoleMapper;

	@Override
	public Integer add(SysEmpCnd sysEmpCnd) {
		sysEmpRoleMapper.deleteByEmpId(sysEmpCnd.getId());
		return sysEmpRoleMapper.batchInsert(sysEmpCnd.getId(), sysEmpCnd.getRoleList());
	}

	@Override
	public List<SysEmpRole> findByEmpId(Long empId) {
		return sysEmpRoleMapper.findByEmpId(empId);
	}

	//TODO
	@Override
	public void deleteByEmpId(Long id) {
		sysEmpRoleMapper.deleteByEmpId(id);
	}

	@Override
	public List<SysEmpRole> findByRoleId(Long id) {
		return sysEmpRoleMapper.findByRoleId(id);
	}
}
