package com.jzy.common.enums;

import lombok.Data;

/**
 * Author : RXK
 * Date : 2019/6/10 14:38
 * Version: V1.0.0
 * Desc:
 **/
public enum TerminalTypeEnum {
	H5(1,"H5端"),
	ADMIN(2,"web端");


	TerminalTypeEnum(int type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	private int type;

	private String msg;


	public int getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}
}
