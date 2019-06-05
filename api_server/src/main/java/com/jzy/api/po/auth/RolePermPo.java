package com.jzy.api.po.auth;

import com.jzy.api.model.auth.SysPermission;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : RXK
 * Date : 2019/6/5 15:44
 * Version: V1.0.0
 * Desc:
 **/
@Data
@NoArgsConstructor
public class RolePermPo {

	private Long roleId;

	private Long permissionId;

	private String permissionKey;

	private Integer permissionType;


	private RolePermPo(Long roleId, Long permissionId, String permissionKey, Integer permissionType) {
		this.roleId = roleId;
		this.permissionId = permissionId;
		this.permissionKey = permissionKey;
		this.permissionType = permissionType;
	}

	public static List<RolePermPo> build(Long id, List<SysPermission> permValues) {
		return permValues.stream()
				  .map(item -> {
					  return new RolePermPo(id, item.getId(), item.getUniqueKey(), item.getPermissionType());
				  })
				  .collect(Collectors.toList());
	}
}
