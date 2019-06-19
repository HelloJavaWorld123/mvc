package com.jzy.api.shiro;

import com.jzy.api.constant.AccessToken;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.vo.auth.SysEmpVo;
import com.jzy.common.enums.TerminalTypeEnum;
import com.jzy.framework.cache.EmpCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.RegExPatternMatcher;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author : RXK
 * Date : 2019/6/6 10:30
 * Version: V1.0.0
 * Desc:
 */
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {

	@Autowired
	private SysEmpService sysEmpService;

	@Resource
	private RedissonClient redissonClient;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		return verifyUri(httpServletRequest, response);
	}

	private boolean verifyUri(HttpServletRequest httpServletRequest, ServletResponse response) {
		String parameter = httpServletRequest.getHeader(AccessToken.APP.getValue());
		if (StringUtils.isNotBlank(parameter)) {
			if (isAdminRequest(parameter)) {
				String apiEmpToken = httpServletRequest.getHeader(AccessToken.EMP.getValue());
				if (isAlreadyLogin(apiEmpToken)) {
					SysEmpVo sysEmpVo = getByToken(apiEmpToken);
					if (Objects.nonNull(sysEmpVo)) {
						UsernamePasswordToken token = new UsernamePasswordToken(sysEmpVo.getName(), sysEmpVo.getPassword());
						getSubject(httpServletRequest, response).login(token);
						return true;
					}
				}
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		String loginUrl = super.getLoginUrl();
		WebUtils.issueRedirect(request, response, loginUrl);
		return false;
	}

	private SysEmpVo getByToken(String apiEmpToken) {
		EmpCache empCache = getFromRedis(apiEmpToken);
		if (Objects.nonNull(empCache)) {
			String empId = empCache.getEmpId();
			SysEmpVo sysEmpVo = sysEmpService.findById(Long.parseLong(empId));
			if (Objects.nonNull(sysEmpVo)) {
				return sysEmpVo;
			}
		}
		return null;
	}

	private EmpCache getFromRedis(String apiEmpToken) {
		RBucket<EmpCache> bucket = redissonClient.getBucket(apiEmpToken);
		return bucket.get();
	}

	private boolean isAlreadyLogin(String apiEmpToken) {
		return StringUtils.isNotBlank(apiEmpToken);
	}

	private boolean isAdminRequest(String value) {
		return Integer.parseInt(value) == TerminalTypeEnum.ADMIN.getType();
	}
}
