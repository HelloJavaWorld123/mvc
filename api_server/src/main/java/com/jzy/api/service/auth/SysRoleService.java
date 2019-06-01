package com.jzy.api.service.auth;

import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.api.model.auth.Role;
import com.jzy.api.vo.auth.SysRoleVo;
import com.jzy.framework.bean.vo.PageVo;

import java.util.List;
import java.util.Set;

/**
 * Author : RXK
 * Date : 2019/5/29 17:36
 * Version: V1.0.0
 * Desc: 系统角色相关接口
 **/
public interface SysRoleService{

	/**
	 * 分页查询 角色列表
	 * @return
	 */
	PageVo<SysRoleVo> list(SysRoleCnd sysRoleCnd);

	/**
	 * 角色新增
	 */
	Integer add(SysRoleCnd sysRoleCnd);

	/**
	 * 根据id 查询角色
	 * @param roleId
	 */
	Role queryById(Long roleId);


	/**
	 * 根据id 更新角色
	 */
	Integer updateById(Role role);

	/**
	 * 逻辑删除角色
	 */
	Integer deleteById(Role role);

	/**
	 * 根据角色id 批量查询
	 */
	List<Role> findByIds(List<Long> roleList);
}
