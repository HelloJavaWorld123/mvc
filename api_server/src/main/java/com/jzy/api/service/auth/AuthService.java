package com.jzy.api.service.auth;

import com.jzy.api.model.auth.Auth;
import com.jzy.api.model.auth.Role;

import java.util.List;
import java.util.Set;

/**
 * <b>功能：</b>权限业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190424&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AuthService {

    /**
     * <b>功能描述：</b>获取用户资源列表<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<Auth> queryMenuList();

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
    Set<String> queryPermissionList(List<Long> roleIds);

    int insertA(Long id, String name);

}
