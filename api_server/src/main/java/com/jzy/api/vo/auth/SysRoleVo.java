package com.jzy.api.vo.auth;

import lombok.Data;

/**
 * Author : RXK
 * Date : 2019/5/29 17:41
 * Version: V1.0.0
 * Desc: 系统角色出参
 **/
@Data
public class SysRoleVo {

	private Long id;

	private String roleName;

	private String roleValue;

	private String description;

	private int status;

}
