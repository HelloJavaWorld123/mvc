package com.jzy.api.model.auth;

import lombok.Data;

@Data
public class SysEmpRole {

    /**
     * 用户id
     */
    private String empId;

    /**
     * 角色表主键
     */
    private String roleId;
}
