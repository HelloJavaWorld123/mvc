package com.jzy.api.model.auth;

import lombok.Data;

/**
 * Author : RXK
 * Date : 2019/5/29 17:18
 * Version: V1.0.0
 * Desc: 角色资源关联表
 **/
@Data
public class SysRolePermission {

	private Long roleId;

	private Long permissionId;

	private String permissionKey;

	private String permissionType;
}
