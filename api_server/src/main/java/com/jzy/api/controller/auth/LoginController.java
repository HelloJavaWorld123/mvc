package com.jzy.api.controller.auth;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.admin.LoginCnd;
import com.jzy.api.constant.AccessToken;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.service.auth.SysEmpRoleService;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.service.auth.SysRolePermissionService;
import com.jzy.api.util.MD5Util;
import com.jzy.api.vo.auth.LogInVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.cache.EmpCache;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author : RXK
 * Date: 2019 06 06
 * desc: 后台管理系统的登录 登出 相关API
 */
@Slf4j
@RestController
@RequestMapping("/sys")
@Api(tags = "后端-权限")
public class LoginController {

	@Autowired
	private SysEmpService sysEmpService;

	@Resource
	private RedissonClient redissonClient;

	@Autowired
	private SysEmpRoleService sysEmpRoleService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;


	/**
	 * TODO 密码前端加密传输 目前是明文
	 */
	@WithoutLogin
	@RequestMapping("/login")
	@ApiOperation(httpMethod = "POST", value = "登录")
	public ApiResult login(@RequestBody @Validated(LoginCnd.LoginValidator.class) LoginCnd loginCnd) {
		UsernamePasswordToken token = new UsernamePasswordToken(loginCnd.getUsername()
																		.trim(), loginCnd.getPwd()
																						 .trim(), loginCnd.getRememberMe());
		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
		} catch (IncorrectCredentialsException | UnknownAccountException | LockedAccountException e) {
			log.error("用户登录异常：", e);
			return new ApiResult().fail("账号或者密码错误", ResultEnum.PERMISSION_DENIED.getCode());
		} catch (UnauthorizedException | AuthenticationException e) {
			return new ApiResult().fail("账号没有正确授权,请联系管理员", ResultEnum.PERMISSION_DENIED.getCode());
		}

		SysEmp sysEmp = (SysEmp) subject.getPrincipal();
		String apiEmpToken = cacheSysEmpInfo(sysEmp);
		LogInVo logInVo = LogInVo.build(sysEmp, apiEmpToken);
		return new ApiResult<>().success(logInVo);
	}

	@WithoutLogin
	@RequestMapping("/unauthorized")
	public ApiResult unauthorized() {
		return new ApiResult().fail(ResultEnum.PERMISSION_DENIED);
	}


	@WithoutLogin
	@RequestMapping("/logout")
	@ApiOperation(httpMethod = "POST", value = "登出")
	public ApiResult logout(HttpServletRequest request) {
		String empId = findEmpId(request);
		if (StringUtils.isNotBlank(empId)) {
			deleteUserCache(empId);
		}
		return new ApiResult().success();
	}

	@RequestMapping("/query")
	@ApiOperation(httpMethod = "POST", value = "查询权限")
	public ApiResult userPermissions(HttpServletRequest request) {
		EmpCache empCache = getEmpCache(request);

		Set<String> permissionValues = getPermissionValues(empCache);

		LogInVo logInVo = LogInVo.build(permissionValues);

		return new ApiResult<>().success(logInVo);
	}

	private Set<String> getPermissionValues(EmpCache empCache) {
		Set<String> permissionValues = new HashSet<>();
		List<Long> roleIds = sysEmpRoleService.findRoleIdsByEmpId(Long.parseLong(empCache.getEmpId()));
		if (CollectionUtils.isNotEmpty(roleIds)) {
			permissionValues = sysRolePermissionService.findByPermissionKeyByRoleIds(roleIds, Long.parseLong(empCache.getEmpId()));
		}
		return permissionValues;
	}


	private EmpCache getEmpCache(HttpServletRequest request) {
		String token = request.getHeader(AccessToken.EMP.getValue());
		Assert.isTrue(StringUtils.isNotBlank(token), "无权限访问");

		RBucket<EmpCache> bucket = redissonClient.getBucket(token);
		Assert.isTrue(bucket.isExists(), "登录信息有误");

		return bucket.get();
	}


	private void deleteUserCache(String empId) {
		if (StringUtils.isNotBlank(empId)) {
			sysEmpService.deleteCache(Long.parseLong(empId));
		}
	}

	private String findEmpId(HttpServletRequest request) {
		String token = request.getHeader(AccessToken.EMP.getValue());
		RBucket<EmpCache> bucket = redissonClient.getBucket(token);
		String empId = null;
		if (bucket.isExists()) {
			empId = bucket.get()
						  .getEmpId();
		}
		return empId;
	}


	private String cacheSysEmpInfo(SysEmp sysEmp) {
		String key = ApiRedisCacheConstant.CACHE_DEALER_EMP + sysEmp.getId();
		String token = MD5Util.string2MD5(key);
		EmpCache value = getEmpCache(sysEmp);
		RBucket<Object> bucket = redissonClient.getBucket(token);
		bucket.set(value);
		return token;
	}

	/**
	 * TODO 后期统一数据类型 目前兼容
	 */
	private EmpCache getEmpCache(SysEmp sysEmp) {
		EmpCache cache = new EmpCache();
		cache.setEmpId(sysEmp.getId()
							 .toString());
		cache.setDealerId(Math.toIntExact(sysEmp.getDealerId()));
		return cache;
	}

}
