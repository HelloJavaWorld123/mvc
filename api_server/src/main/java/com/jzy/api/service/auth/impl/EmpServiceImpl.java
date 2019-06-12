package com.jzy.api.service.auth.impl;

import com.jzy.api.dao.auth.EmpMapper;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.service.auth.EmpService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：</b>登录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Service
public class EmpServiceImpl extends GenericServiceImpl<Emp> implements EmpService {

    @Resource
    private EmpMapper empMapper;

    /**
     * <b>功能描述：</b>用户名称校验<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<Emp> checkNameList(String name,String id) {
        return  empMapper.checkNameList(name,id);
    }

    @Override
    protected GenericMapper<Emp> getGenericMapper() {
        return empMapper;
    }
}
