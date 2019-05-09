package com.jzy.api.service.sys.impl;

import com.jzy.api.dao.sys.UserMapper;
import com.jzy.api.model.sys.User;
import com.jzy.api.service.sys.UserService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <b>功能：</b>前台用户信息保存<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190509&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    protected GenericMapper<User> getGenericMapper() {
        return userMapper;
    }
}
