package com.jzy.api.service.dealer.impl;

import com.jzy.api.dao.dealer.EmpMapper;
import com.jzy.api.model.dealer.Emp;
import com.jzy.api.service.dealer.EmpService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import freemarker.core.BugException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * <b>功能描述：</b>登录<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param username 用户名
     * @param pwd 密码
     */
    @Override
    public void login(String username, String pwd) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
        token.setRememberMe(false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException ex) {
            throw new BusException("用户名没有找到");
        } catch (IncorrectCredentialsException ex) {
            throw new BusException("用户名密码不匹配");
        }catch (AuthenticationException e) {
            throw new BusException("其他的登录错误");
        }
    }

    /**
     * <b>功能描述：</b>根据用户名查询用户<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public Emp queryEmpByUsername(String username) {
        Emp emp = empMapper.queryEmpByUsername(username);
        if (emp == null) {
            throw new BugException("当前用户不存在");
        }
        return emp;
    }

    @Override
    protected GenericMapper<Emp> getGenericMapper() {
        return empMapper;
    }
}
