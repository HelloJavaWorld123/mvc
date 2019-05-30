package com.jzy.api.service.auth;

import com.jzy.api.model.auth.SysRolePermission;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 15:28
 * Version: V1.0.0
 * Desc: 角色资源管理相关方法
 **/
public interface SysRolePermissionService {
	/**
	 * 为指定的角色分配 资源
	 * @param id ：角色id
	 * @param permValues ：资源key
	 * @param permType
	 */
	Integer add(Long id, List<String> permValues, Integer permType);


	List<SysRolePermission> findByRoleId(Long roleId);

}
