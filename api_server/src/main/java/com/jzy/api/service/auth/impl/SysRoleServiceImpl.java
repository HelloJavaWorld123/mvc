package com.jzy.api.service.auth.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.api.dao.auth.SysRoleMapper;
import com.jzy.api.model.auth.Role;
import com.jzy.api.service.auth.SysRoleService;
import com.jzy.api.vo.auth.SysRoleVo;
import com.jzy.framework.bean.vo.PageVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Author : RXK
 * Date : 2019/5/29 17:37
 * Version: V1.0.0
 * Desc: 系统角色管理
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {

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
	public Integer deleteById(Role role) {
		return sysRoleMapper.updateDelFLag(role.getId());
	}

	@Override
	public List<Role> findByIds(List<Long> roleList) {
		return sysRoleMapper.findByIds(roleList);
	}
}
