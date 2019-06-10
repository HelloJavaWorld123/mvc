package com.jzy.api.service.auth;

import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.vo.auth.SysPermissionVo;
import com.jzy.framework.bean.vo.PageVo;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 10:08
 * Version: V1.0.0
 * Desc: 权限资源相关方法
 **/
public interface SysPermissionService {
	/**
	 * 查询权限资源分页列表
	 */
	List<SysPermissionVo> list(SysPermissionCnd permissionCnd);


	/**
	 * 新增
	 */
	Integer add(SysPermission permissionCnd);


	/**
	 * 更新
	 */
	Integer update(SysPermission permissionCnd);

	/**
	 * 删除
	 */
	Integer delete(Long permissionId);

	/**
	 * 根据id 查询
	 * @return
	 */
	SysPermission findById(Long id);

	/**
	 * 根据key  查找指定的 资源
	 * @param permValues ：资源唯一Key 值
	 */
	List<SysPermission> findByKeys(List<String> permValues);

	/**
	 * 根据key 查询
	 * @param uniqueKey ：唯一的key
	 */
	SysPermission findByUniqueKey(String uniqueKey);

	/**
	 * 查询当前ids 下所有的 资源
	 * @param permIds ：id值
	 */
	List<SysPermission> findByIds(List<Long> permIds);

	/**
	 * 查询所有的API接口
	 * @return ：
	 */
	List<SysPermission> findAllApiList();

}
