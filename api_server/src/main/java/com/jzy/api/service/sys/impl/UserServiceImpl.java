package com.jzy.api.service.sys.impl;

import com.jzy.api.dao.sys.UserMapper;
import com.jzy.api.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl {

    @Resource
    private UserMapper userMapper;

}
