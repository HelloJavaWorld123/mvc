package com.jzy.api.service.auth.impl;

import com.jzy.api.dao.auth.AuthMapper;
import com.jzy.api.model.auth.Auth;
import com.jzy.api.model.auth.Role;
import com.jzy.api.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthMapper authMapper;

    /**
     * <b>功能描述：</b>根据用户id获取角色信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public Set<Role> queryRoleList(Long userId) {
        return authMapper.queryRoleList(userId);
    }

    /**
     * <b>功能描述：</b>根据角色id获取所有权限信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public Set<String> queryPermissionList(List<Long> roleIds) {
        return authMapper.queryPermissionList(roleIds);
    }

    /**
     * <b>功能描述：</b>获取用户资源列表<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param userId 用户id
     */
    @Override
    public List<Auth> getResource(Long userId) {
        log.error("123456");
        return null;
    }

}