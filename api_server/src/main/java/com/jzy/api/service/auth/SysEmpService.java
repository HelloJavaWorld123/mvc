package com.jzy.api.service.auth;

import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.vo.auth.SysEmpVo;
import com.jzy.framework.bean.vo.PageVo;

/**
 * Author : RXK
 * Date : 2019/5/30 12:11
 * Version: V1.0.0
 * Desc: 后台用户管理
 **/
public interface SysEmpService {

	/**
	 * 分页查询所有的用户信息
	 */
	PageVo<SysEmpVo> list(SysEmpCnd sysEmpCnd);

	/**
	 * 新增用户
	 */
	Integer add(SysEmpCnd sysEmpCnd);

	/**
	 * 根据id查找指定的用户
	 */
	SysEmpVo findById(Long id);

	/**
	 * 更新用户的信息
	 */
	Integer update(SysEmpCnd sysEmpCnd);

	/**
	 * 逻辑删除用户
	 */
	Integer deleteById(Long id);
}
