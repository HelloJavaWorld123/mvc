package com.jzy.api.service.auth;

import com.jzy.api.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

/**
 * Author : RXK
 * Date : 2019/5/31 17:06
 * Version: V1.0.0
 * Desc: 自定义密码比对
 **/
@Component
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String tokenPassword = (String) getCredentials(info);
		String infoPassword = (String) getCredentials(token);
		String md5 = MD5Util.string2MD5(infoPassword);
		return StringUtils.equals(tokenPassword,md5);
	}
}
