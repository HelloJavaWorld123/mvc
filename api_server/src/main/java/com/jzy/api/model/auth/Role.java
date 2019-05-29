package com.jzy.api.model.auth;

import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends GenericModel {

    private String roleName;

    private String roleValue;

    private String description;

    private int status;

    private Role(Long id, Date modifyTime, Long modifierId, String roleName, String roleValue, String description, int status) {
        super(id, modifyTime, modifierId);
        this.roleName = roleName;
        this.roleValue = roleValue;
        this.description = description;
        this.status = status;
    }

    private Role(Integer delFlag, Date createTime, Long creatorId, Date modifyTime, Long modifierId, String roleName, String roleValue, String description, int status) {
        super(delFlag, createTime, creatorId, modifyTime, modifierId);
        this.roleName = roleName;
        this.roleValue = roleValue;
        this.description = description;
        this.status = status;
    }


    private Role(String roleName, String roleValue, String description, int status) {
        this(0, new Date(), 1L, new Date(), 1L,roleName,roleValue,description,status);
    }


    public static Role build(SysRoleCnd sysRoleCnd) {
        return new Role(sysRoleCnd.getRoleName(), sysRoleCnd.getRoleValue(), sysRoleCnd.getDescription(), sysRoleCnd.getStatus());
    }

    public static Role update(SysRoleCnd sysRoleCnd) {
        return new Role(sysRoleCnd.getId(),new Date(),2L,sysRoleCnd.getRoleName(),sysRoleCnd.getRoleValue(),sysRoleCnd.getDescription(),sysRoleCnd.getStatus());
    }
}
