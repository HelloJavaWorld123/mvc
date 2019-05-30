package com.jzy.api.dao.sys;

import com.jzy.api.model.auth.SysEmpRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b>职务角色表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190511&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface SysEmpRoleMapper {

    /**
     * <b>功能描述：</b>新增<br>
     * <b>修订记录：</b><br>
     * <li>20190511&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int insert(@Param("sysEmpRole") SysEmpRole sysEmpRole);

	/**
	 * 删除指定用户下的所有的关联关系
	 * @param id ：当前用户的id
	 */
	void deleteByEmpId(@Param("id") Long id);

	/**
	 * 指定用户新增关联关系
	 * @param id ：指定的用户
	 * @param roleList ：角色id
	 */
	Integer batchInsert(@Param("empId") Long id, @Param("roleList") List<Long> roleList);

	List<SysEmpRole> findByEmpId(Long empId);
}
