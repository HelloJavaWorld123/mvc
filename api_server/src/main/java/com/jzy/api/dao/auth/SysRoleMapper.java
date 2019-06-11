package com.jzy.api.dao.auth;

import com.jzy.api.cnd.auth.SysRoleCnd;
import com.jzy.api.model.auth.Role;
import com.jzy.api.vo.auth.SysRoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/29 17:37
 * Version: V1.0.0
 * Desc: 系统角色dao层
 **/
public interface SysRoleMapper{

	/**
	 * 角色新增
	 * @return
	 */
	Integer add(@Param("role") Role role);

	/**
	 * 根据主键查询 具体的角色
	 */
	Role queryById(@Param("id") Long id);

	/**
	 * 角色信息更新
	 */
	Integer update(@Param("role") Role role);

	/**
	 * 逻辑删除
	 */
	Integer updateDelFLag(@Param("id") Long id);

	/**
	 * 角色列表查询
	 */
	List<SysRoleVo> list(@Param("sysRoleCnd") SysRoleCnd sysRoleCnd);

	List<Role> findByIds(@Param("roleList") List<Long> roleList);

	Role findByRoleValue(@Param("roleValue") String roleValue, @Param("roleId") Long roleId);

	Role findByRoleName(String name);
}
