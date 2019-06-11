package com.jzy.api.controller.auth;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.admin.LoginCnd;
import com.jzy.api.constant.AccessToken;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.service.auth.EmpService;
import com.jzy.api.service.auth.SysEmpRoleService;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.service.auth.SysRolePermissionService;
import com.jzy.api.util.MD5Util;
import com.jzy.api.vo.sys.EmpVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.cache.EmpCache;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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

	@Autowired
	private SysEmpRoleService sysEmpRoleService;

	@Autowired
	private SysRolePermissionService sysRolePermissionService;

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
		} catch (IncorrectCredentialsException | UnknownAccountException | LockedAccountException e) {
			log.error("用户登录异常：", e);
			return new ApiResult().fail("账号或者密码错误",ResultEnum.PERMISSION_DENIED.getCode());
		}catch (UnauthorizedException | AuthenticationException e){
			return new ApiResult().fail("账号没有正确授权,请联系管理员",ResultEnum.PERMISSION_DENIED.getCode());
		}

		SysEmp sysEmp = (SysEmp) subject.getPrincipal();
		String apiEmpToken = cacheSysEmpInfo(sysEmp);
		EmpVo empVo = EmpVo.build(sysEmp,apiEmpToken);
		return new ApiResult<>().success(empVo);
	}

	@WithoutLogin
	@RequestMapping("/unauthorized")
	public ApiResult unauthorized() {
		return new ApiResult().fail(ResultEnum.PERMISSION_DENIED);
	}


	@WithoutLogin
	@RequestMapping("/logout")
	public ApiResult logout(HttpServletRequest request) {
		String empId = deleteToken(request);
		deleteUserCache(empId);
		return new ApiResult().success();
	}

	@RequestMapping("/query")
	public ApiResult userPermissions(HttpServletRequest request){
		EmpCache empCache = getEmpCache(request);

		Set<String> permissionValues = getPermissionValues(empCache);

		EmpVo empVo = EmpVo.build(permissionValues);

		return new ApiResult<>().success(empVo);
	}

	private Set<String> getPermissionValues(EmpCache empCache) {
		Set<String> permissionValues = new HashSet<>();
		List<Long> roleIds = sysEmpRoleService.findRoleIdsByEmpId(Long.parseLong(empCache.getEmpId()));
		if(CollectionUtils.isNotEmpty(roleIds)){
			permissionValues = sysRolePermissionService.findByPermissionKeyByRoleIds(roleIds, Long.parseLong(empCache.getEmpId()));
		}
		return permissionValues;
	}


	private EmpCache getEmpCache(HttpServletRequest request){
		String token = request.getHeader(AccessToken.EMP.getValue());
		Assert.isTrue(StringUtils.isBlank(token),"无权限访问");

		RBucket<EmpCache> bucket = redissonClient.getBucket(token);
		Assert.isTrue(bucket.isExists(),"登录信息有误");

		return bucket.get();
	}


	private void deleteUserCache(String empId) {
		if (StringUtils.isNotBlank(empId)) {
			sysEmpService.deleteCache(Long.parseLong(empId));
		}
	}

	private String deleteToken(HttpServletRequest request) {
		String token = request.getHeader(AccessToken.EMP.getValue());
		RBucket<EmpCache> bucket = redissonClient.getBucket(token);
		String empId = null;
		if(bucket.isExists()){
			empId = bucket.get()
						  .getEmpId();
			bucket.deleteAsync();
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

	//TODO 后期统一数据类型 目前兼容
	private EmpCache getEmpCache(SysEmp sysEmp) {
		EmpCache cache = new EmpCache();
		cache.setEmpId(sysEmp.getId()
							 .toString());
		cache.setDealerId(Math.toIntExact(sysEmp.getDealerId()));
		return cache;
	}

}
