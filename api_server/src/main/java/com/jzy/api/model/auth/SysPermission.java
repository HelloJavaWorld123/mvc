package com.jzy.api.model.auth;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author : RXK
 * Date : 2019/5/29 17:14
 * Version: V1.0.0
 * Desc: 权限资源表
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends GenericModel {

	private int terminal;

	private String uniqueKey;

	private String parentKey;

	private String permissionName;

	private int permissionType;

	private int leafNode;

	private String description;

}
