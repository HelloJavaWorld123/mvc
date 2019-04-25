package com.jzy.api.base;

import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.User;
import com.jzy.api.service.auth.AuthService;
import com.jzy.framework.exception.BusException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    protected static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Resource
    private AuthService authService;

    /**
     * <b>功能描述：</b>授权<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        logger.debug( "用户id为: " + user.getId());
        //查到权限数据，返回授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户id获取用户所对应的角色列表
        Set<Role> roleList = authService.queryRoleList(user.getId());
        if (roleList == null || roleList.isEmpty()) {
            throw new BusException("当前用户没有角色设置！");
        }
        Set<String> roleNames = new HashSet<>();
        List<Long> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
            roleNames.add(role.getName());
        }
        // 根据角色id查询权限列表信息
        Set<String> permissionList = authService.queryPermissionList(roleIds);
        if (permissionList == null || permissionList.isEmpty()) {
            throw new BusException("当前用户没有权限设置！");
        }
        simpleAuthorizationInfo.setRoles(roleNames);
        simpleAuthorizationInfo.setStringPermissions(permissionList);
        return simpleAuthorizationInfo;
    }

    /**
     * <b>功能描述：</b>认证用户<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        // 根据用户id获取用户信息

        // 根据用户id获取账户信息

        User user = new User();
        user.setId(2L);
        user.setUsername("xt");
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user, user.getPassword(), username);
        return simpleAuthenticationInfo;
    }

}
