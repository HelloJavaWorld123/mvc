package com.jzy.api.vo.auth;

import com.jzy.api.model.auth.SysEmp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author : RXK
 * Date : 2019/6/12 15:56
 * Version: V1.0.0
 * Desc:
 **/
@Data
@NoArgsConstructor
public class LogInVo {
	/**
	 * 用户名
	 */
	private String name;

	/**
	 * token
	 */
	private String apiEmpToken;

	/**
	 * 当前用户的 权限集合
	 */
	private Set<String> permValues;

	private LogInVo(String name, String apiEmpToken) {
		this.name = name;
		this.apiEmpToken = apiEmpToken;
	}

	private LogInVo(Set<String> permValues) {
		this.permValues = permValues;
	}

	public static LogInVo build(SysEmp sysEmp, String apiEmpToken){
		return new LogInVo(sysEmp.getName(), apiEmpToken);
	}

	public static LogInVo build(Set<String> permissionValues) {
		return new LogInVo(permissionValues);
	}
}
