package com.jzy.api.dao.auth;

import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.vo.auth.SysPermissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 11:06
 * Version: V1.0.0
 * Desc: 权限资源 相关操作
 **/
public interface SysPermissionMapper {
	/**
	 * 查询
	 */
	List<SysPermissionVo> list(@Param("permission") SysPermissionCnd permission);

	/**
	 * 新增
	 */
	Integer add(@Param("permission") SysPermission permission);

	/**
	 * 更新
	 */
	Integer update(@Param("permission") SysPermission permission);


	/**
	 * 删除
	 */
	Integer delete(@Param("id") Long permissionId);

	/**
	 * 查询指定的信息
	 */
	SysPermission findById(@Param("id") Long id);

	/**
	 * 根据资源的唯一键
	 * @param permValues ：资源唯一的key
	 */
	List<SysPermission> findByUniqueKeys(@Param("permValues") List<String> permValues);
}
