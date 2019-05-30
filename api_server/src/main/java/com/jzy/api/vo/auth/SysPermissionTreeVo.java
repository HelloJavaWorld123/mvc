package com.jzy.api.vo.auth;

import lombok.Data;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 16:18
 * Version: V1.0.0
 * Desc: 资源树形输出参数
 **/
@Data
public class SysPermissionTreeVo {

	//菜单列
	private List<SysPermissionVo> menuList;


	//按钮列
	private List<SysPermissionVo> buttonList;


	//api接口列
	private List<SysPermissionVo> apiList;

}
