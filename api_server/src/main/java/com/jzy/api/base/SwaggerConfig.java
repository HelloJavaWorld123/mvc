//package com.jzy.api.base;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Author : RXK
// * Date : 2019/5/30 17:42
// * Version: V1.0.0
// * Desc: swagger配置
// **/
//@Configuration
//@EnableSwagger2
//@EnableWebMvc
//public class SwaggerConfig {
//
//
//	@Bean
//	public Docket docket() {
//		return new Docket(DocumentationType.SPRING_WEB).forCodeGeneration(false)
//													  .useDefaultResponseMessages(false)
//													  .groupName("RBAC")
//													  .apiInfo(apiInfo())
//													  .select()
//													  .apis(RequestHandlerSelectors.basePackage("com.jzy.controller.auth"))
//													  .paths(PathSelectors.regex("/sys/**"))
//													  .build();
//	}
//
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("SERVER API")
//								   .description("SERVER API 接口文档")
//								   .version("V1.0.0")
//								   .license("Apache 2.0")
//								   .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
//								   //							.contact(new Contact("JZY", "http://mall.900sup.com", "029-82275008"))
//								   .build();
//	}
//
//
//}
