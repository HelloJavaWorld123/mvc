package com.jzy.api.controller.sys;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.admin.LoginCnd;
import com.jzy.api.constant.AccessToken;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.service.auth.EmpService;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.util.MD5Util;
import com.jzy.api.vo.sys.EmpVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.cache.EmpCache;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : RXK
 * Date: 2019 06 06
 * desc: 后台管理系统的登录 登出 相关API
 */
@Slf4j
@RestController
@RequestMapping(value = {"/login", "/sys"})
public class LoginController extends GenericController {

	@Resource
	private EmpService userService;

	@Autowired
	private SysEmpService sysEmpService;

	@Resource
	private RedissonClient redissonClient;

	/**
	 * <b>功能描述：</b>获取用户资源列表<br>
	 * <b>修订记录：</b><br>
	 * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 */
	@WithoutLogin
	@RequestMapping(path = "/managerLogin")
	public ApiResult managerLogin(@RequestBody LoginCnd loginCnd) {
		ApiResult<EmpVo> apiResult = new ApiResult<>();
		Emp emp = userService.login(loginCnd.getUsername(), loginCnd.getPwd());
		EmpVo empVo = new EmpVo();
		empVo.setName(emp.getName());
		// TODO: 2019/4/28 目前只返回一个角色名即可
		List<Role> roleList = new ArrayList<>(emp.getRoles());
		if (!roleList.isEmpty()) {
			//            empVo.setRoleName(roleList.get(0).getName());
		}
		empVo.setApiEmpToken(emp.getApiEmpToken());
		return apiResult.success(empVo);
	}


	/**
	 * TODO 密码前端加密传输 目前是明文
	 */
	@WithoutLogin
	@RequestMapping("/login")
	public ApiResult login(@RequestBody @Validated(LoginCnd.LoginValidator.class) LoginCnd loginCnd) {
		UsernamePasswordToken token = new UsernamePasswordToken(loginCnd.getUsername()
																		.trim(), loginCnd.getPwd()
																						 .trim(), loginCnd.getRememberMe());
		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
		} catch (UnknownAccountException | CredentialsException | LockedAccountException | UnauthorizedException e) {
			log.error("用户登录异常：", e);
			return new ApiResult().fail(e.getMessage());
		}

		SysEmp sysEmp = (SysEmp) subject.getPrincipal();
		String apiEmpToken = cacheSysEmpInfo(sysEmp);
		EmpVo empVo = EmpVo.build(sysEmp, apiEmpToken);
		return new ApiResult<>().success(empVo);
	}

	@WithoutLogin
	@RequestMapping("/unauthorized")
	public ApiResult unauthorized(HttpServletResponse response) {
		try {
			WebUtils.toHttp(response)
					.sendError(ResultEnum.SESSION_VALID.getCode());
		} catch (IOException e) {
			return new ApiResult().fail(ResultEnum.SESSION_VALID);
		}
		return new ApiResult().fail(ResultEnum.SESSION_VALID);
	}


	@WithoutLogin
	@RequestMapping("/logout")
	public ApiResult logout(HttpServletRequest request) {
		deleteToken(request);
		deleteUserCache();
		return new ApiResult().success();
	}

	private void deleteUserCache() {
		SysEmp sysEmp = (SysEmp) SecurityUtils.getSubject()
											  .getPrincipal();
		if (Objects.nonNull(sysEmp)) {
			sysEmpService.deleteCache(sysEmp.getId());
		}
	}

	private void deleteToken(HttpServletRequest request) {
		String token = request.getHeader(AccessToken.EMP.getValue());
		RBucket<EmpCache> bucket = redissonClient.getBucket(token);
		if(bucket.isExists()){
			bucket.deleteAsync();
		}
	}


	private String cacheSysEmpInfo(SysEmp sysEmp) {
		String key = ApiRedisCacheConstant.CACHE_DEALER_EMP + sysEmp.getId();
		String token = MD5Util.string2MD5(key);
		EmpCache value = getEmpCache(sysEmp);
		RBucket<Object> bucket = redissonClient.getBucket(token);
		bucket.set(value);
		return token;
	}

	//TODO 后期统一数据类型 目前兼容
	private EmpCache getEmpCache(SysEmp sysEmp) {
		EmpCache cache = new EmpCache();
		cache.setEmpId(sysEmp.getId()
							 .toString());
		cache.setDealerId(Math.toIntExact(sysEmp.getDealerId()));
		return cache;
	}

}
