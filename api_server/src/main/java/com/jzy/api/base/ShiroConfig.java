package com.jzy.api.base;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author : RXK
 * Date : 2019/5/31 17:14
 * Version: V1.0.0
 * Desc: 配置过滤器 等 Bean
 **/
@Configuration
public class ShiroConfig {


	@Value("#{'${anon.api}'.split(',')}")
	private List<String> ignoreApis;


	@Bean
	public ShiroFilterChainDefinition definition() {
		DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
		chainDefinition.addPathDefinitions(ignoreApis());
		chainDefinition.addPathDefinition("/**","authc");
		return chainDefinition;
	}


	private Map<String, String> ignoreApis() {
		Map<String, String> maps = new HashMap<>(getIgnoreApis().size());
		if (CollectionUtils.isNotEmpty(getIgnoreApis())) {
			for (String api : getIgnoreApis()) {
				maps.put(api, "anon");
			}
		}
		return maps;
	}


	private List<String> getIgnoreApis() {
		return ignoreApis;
	}
}
