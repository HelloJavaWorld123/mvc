package com.jzy.api.dao.auth;

import com.jzy.api.model.auth.SysRolePermission;
import com.jzy.api.po.auth.RolePermPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 15:37
 * Version: V1.0.0
 * Desc:  角色资源相关的操作
 **/
public interface SysRolePermissionMapper {

	void deleteByRoleId(Long roleId);

	List<SysRolePermission> findByRoleId(@Param("roleId") Long roleId);

	List<SysRolePermission> findByRoleIds(@Param("roleIds") List<Long> roleIds);

	Integer batchAdd(@Param("rolePermPos") List<RolePermPo> rolePermPos);
}
