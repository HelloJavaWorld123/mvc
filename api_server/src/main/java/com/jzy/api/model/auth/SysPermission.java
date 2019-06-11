package com.jzy.api.model.auth;

import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author : RXK
 * Date : 2019/5/29 17:14
 * Version: V1.0.0
 * Desc: 权限资源表
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysPermission extends GenericModel {

	private String uniqueKey;

	private String parentKey;

	private String permissionName;

	private int permissionType;

	private int leafNode;

	private int permissionStatus;

	private String description;


	private SysPermission(Integer delFlag, Date createTime, Long creatorId, Date modifyTime, Long modifierId, String uniqueKey, String parentKey, String permissionName, int permissionType, int leafNode,int permissionStatus, String description) {
		super(delFlag, createTime, creatorId, modifyTime, modifierId);
		this.uniqueKey = uniqueKey;
		this.parentKey = parentKey;
		this.permissionName = permissionName;
		this.permissionType = permissionType;
		this.leafNode = leafNode;
		this.permissionStatus = permissionStatus;
		this.description = description;
	}

	private SysPermission(Long id, Date modifyTime, Long modifierId, String uniqueKey, String parentKey, String permissionName, int permissionType, int leafNode, int permissionStatus,String description) {
		super(id, modifyTime, modifierId);
		this.uniqueKey = uniqueKey;
		this.parentKey = parentKey;
		this.permissionName = permissionName;
		this.permissionType = permissionType;
		this.leafNode = leafNode;
		this.permissionStatus = permissionStatus;
		this.description = description;
	}

	public static SysPermission build(SysPermissionCnd permissionCnd) {
		return new SysPermission(0, new Date(), permissionCnd.getOperatorId(), new Date(), permissionCnd.getOperatorId(), permissionCnd.getUniqueKey(), permissionCnd.getParentKey(), permissionCnd.getPermName(), permissionCnd.getPermType(), permissionCnd.getLeafNode(), permissionCnd.getPermStatus(),permissionCnd.getDescription());
	}

	public static SysPermission update(SysPermissionCnd permissionCnd) {
		return new SysPermission(permissionCnd.getId(), new Date(), permissionCnd.getOperatorId(), permissionCnd.getUniqueKey(), permissionCnd.getParentKey(), permissionCnd.getPermName(), permissionCnd.getPermType(),permissionCnd.getLeafNode(), permissionCnd.getPermStatus(),permissionCnd.getDescription());
	}
}
