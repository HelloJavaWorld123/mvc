package com.jzy.api.service.sys.impl;

import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.dao.sys.EmpMapper;
import com.jzy.api.model.auth.Role;
import com.jzy.framework.cache.EmpCache;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.service.auth.AuthService;
import com.jzy.api.service.sys.EmpService;
import com.jzy.api.util.MD5Util;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import freemarker.core.BugException;
import lombok.extern.slf4j.Slf4j;
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
        Emp emp = queryEmpByUsername(username);
        if (MD5Util.string2MD5(pwd).equals(emp.getPwd())) {
            throw new BusException("密码不正确！");
        }
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
        String cacheDealerEmp = ApiRedisCacheConstant.CACHE_DEALER_EMP + emp.getId();
        String token = MD5Util.string2MD5(cacheDealerEmp);
        EmpCache empCache = new EmpCache();
        empCache.setEmpId(emp.getId());
        empCache.setDealerId(emp.getDealerId());
        RBucket<EmpCache> bucket = redissonClient.getBucket(token);
        bucket.set(empCache);
        emp.setApiEmpToken(token);
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
