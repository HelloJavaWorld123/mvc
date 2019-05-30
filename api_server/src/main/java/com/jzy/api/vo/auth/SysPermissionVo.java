package com.jzy.api.vo.auth;

import lombok.Data;

/**
 * Author : RXK
 * Date : 2019/5/30 10:19
 * Version: V1.0.0
 * Desc:
 **/
@Data
public class SysPermissionVo {
	private Long id;

	private String uniqueKey;

	private String parentKey;

	private String permissionName;

	private int permissionType;

	private int leafNode;

	private String description;



}
