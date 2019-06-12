package com.jzy.api.dao.auth;

import com.jzy.api.model.auth.Auth;
import com.jzy.api.model.auth.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AuthMapper {

    /**
     * <b>功能描述：</b>获取用户资源列表<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<Auth> queryMenuList(Long dealerId);

    /**
     * <b>功能描述：</b>根据用户id获取角色信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param empId 用户id
     */
    Set<Role> queryRoleList(Long empId);

    /**
     * <b>功能描述：</b>根据角色id获取所有权限信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param roleIds 角色列表
     */
    Set<String> queryPermissionList(@Param("roleIds") List<Long> roleIds);

    int insert(@Param("id") Long id, @Param("name") String name);

}
