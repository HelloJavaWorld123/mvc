package com.jzy.api.service.auth;

import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.po.auth.RolePermPo;

import java.util.List;
import java.util.Set;

/**
 * @Author : RXK
 * Date : 2019/5/30 15:28
 * Version: V1.0.0
 * Desc: 角色资源管理相关方法
 **/
public interface SysRolePermissionService {
	/**
	 * 批量添加
	 */
	Integer add(List<RolePermPo> rolePermPos);


	List<SysRolePermission> findByRoleId(Long roleId);

	/**
	 * 更具角色id  查询所有的资源key
	 */
	List<SysRolePermission> findByRoleIds(List<Long> roleIds);

	/**
	 * 根据角色id 以及 类型删除 已经分配的资源
	 * @param roleId ： 角色id
	 * @param permType ：资源类型
	 */
	void deleteByRoleIdAndPermType(Long roleId, Integer permType);

	/**
	 * 根据角色id 查询 当前用户拥有的资源key
	 * @param roleIds :角色ids
	 * @param id :当前用户的id
	 * @return null 没有分配权限
	 */
	Set<String> findByPermissionKeyByRoleIds(List<Long> roleIds, Long id);
}
