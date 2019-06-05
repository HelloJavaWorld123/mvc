package com.jzy.api.service.auth;

import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.po.auth.RolePermPo;

import java.util.List;

/**
 * Author : RXK
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
}
