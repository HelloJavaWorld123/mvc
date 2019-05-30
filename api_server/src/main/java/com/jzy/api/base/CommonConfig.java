package com.jzy.api.base;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Validator;

/**
 * Author : RXK
 * Date : 2019/5/30 21:56
 * Version: V1.0.0
 * Desc:
 **/
@Configuration
public class CommonConfig extends WebMvcConfigurerAdapter {

	@Bean
	public Validator validator(){
		return new LocalValidatorFactoryBean();
	}

}
