package com.jzy.api.vo.auth;

import lombok.Data;

/**
 * Author : RXK
 * Date : 2019/5/30 12:14
 * Version: V1.0.0
 * Desc:
 **/
@Data
public class SysEmpVo {

	private Long id;

	private String name;

	private String password;

	private int status;

	private Long dealerId;

}
