package com.jzy.api.service.auth;

import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.vo.auth.SysEmpVo;
import com.jzy.framework.bean.vo.PageVo;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 12:11
 * Version: V1.0.0
 * Desc: 后台用户管理
 **/
public interface SysEmpService {

	/**
	 * 分页查询所有的用户信息
	 *
	 * 不包括密码字段
	 */
	PageVo<SysEmpVo> list(SysEmpCnd sysEmpCnd);

	/**
	 * 新增用户
	 * @param sysEmp
	 */
	Integer add(SysEmp sysEmp) throws DuplicateKeyException;

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
	Integer deleteById(Long id, Long operatorId);

	/**
	 * 根据用户名 查询
	 * 包括用户状态 为 1 的账户
	 * 当不包括 已经删除的用户
	 * @return
	 */
	List<SysEmp> findByName(String name, Long userId);
}
