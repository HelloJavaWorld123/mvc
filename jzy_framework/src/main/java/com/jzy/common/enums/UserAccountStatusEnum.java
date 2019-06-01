package com.jzy.common.enums;

/**
 * Author : RXK
 * Date : 2019/5/31 14:47
 * Version: V1.0.0
 * Desc: 用户账号 状态
 **/
public enum UserAccountStatusEnum {

	NORMAL_STATUS(0,"正常"),
	STOP_STATUS(1,"停用")
	;

	UserAccountStatusEnum(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	private int status;

	private String msg;

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}
}
