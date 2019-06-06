package com.jzy.api.shiro;

import com.jzy.api.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * Author : RXK
 * Date : 2019/5/31 17:06
 * Version: V1.0.0
 * Desc: 自定义密码比对
 **/
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String rightPassword = (String) getCredentials(info);
		char[] credentials = (char[]) token.getCredentials();
		String needVerifyPassword = new String(credentials);
		if(rightPassword.equalsIgnoreCase(needVerifyPassword)){
			return Boolean.TRUE;
		}
		String md5 = MD5Util.string2MD5(needVerifyPassword);
		return StringUtils.equals(rightPassword,md5);
	}
}
