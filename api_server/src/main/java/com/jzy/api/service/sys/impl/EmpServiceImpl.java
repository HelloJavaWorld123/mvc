package com.jzy.api.service.sys.impl;

import com.jzy.api.constant.ApiRedisCacheContant;
import com.jzy.api.dao.sys.EmpMapper;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.cache.EmpCache;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.service.auth.AuthService;
import com.jzy.api.service.sys.EmpService;
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
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

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

    @Resource
    private AuthService authService;

    @Resource
    private RedissonClient redissonClient;

    /**
     * <b>功能描述：</b>登录<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param username 用户名
     * @param pwd 密码
     */
    @Override
    public Emp login(String username, String pwd) {
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
        Emp emp = queryEmpByUsername(username);
        // 缓存渠道商员工信息
        cacheDealerEmp(emp);
        Set<Role> roles = authService.queryRoleList(emp.getId());
        emp.setRoles(roles);
        return emp;
    }

    /**
     * <b>功能描述：</b>缓存渠道商员工信息<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void cacheDealerEmp(Emp emp) {
        String cacheDealerEmp = ApiRedisCacheContant.CACHE_DEALER_EMP + emp.getId();
        RBucket<EmpCache> bucket = redissonClient.getBucket(ApiRedisCacheContant.CACHE_DEALER_EMP + emp.getId());
        EmpCache empCache = new EmpCache();
        empCache.setEmpId(emp.getId());
        empCache.setDealerId(emp.getDealerId());
        bucket.set(empCache);
        emp.setDEALER_EMP_TOKEN(cacheDealerEmp);
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
