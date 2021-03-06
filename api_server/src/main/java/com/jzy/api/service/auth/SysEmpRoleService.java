package com.jzy.api.service.auth;

import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.model.auth.SysEmpRole;

import java.util.List;

/**
 * @Author : RXK
 * Date : 2019/5/30 14:39
 * Version: V1.0.0
 * Desc: 用户角色关联相关方法
 **/
public interface SysEmpRoleService {

	/**
	 * 为指定的用户 新增角色
	 * @return
	 */
	Integer add(SysEmpCnd sysEmpCnd);


	List<SysEmpRole> findByEmpId(Long empId);

	/**
	 * 根据用户id 删除角色关联关系
	 * @param id ：用户id
	 */
	void deleteByEmpId(Long id);

	/**
	 * 根据角色id 查询
	 * @param id ：角色id
	 */
	List<SysEmpRole> findByRoleId(Long id);

	/**
	 * 查询当前用户 所有的角色id
	 * @param id ：当前用户的id
	 * @return ：null 暂无分配角色
	 */
	List<Long> findRoleIdsByEmpId(Long id);
}
