package com.jzy.api.base;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @ClassNameName Swagger2Config
 * @Description TODO
 * @Author jiazhiyi
 * @DATE 2019/6/12
 * @Version 1.0
 **/
//@Configuration
@EnableWebMvc
@EnableSwagger2
@Component
public class Swagger2Config {
    private final static Logger logger = LoggerFactory.getLogger(Swagger2Config.class);

    private static String online;

    public static String getOnline() {
        return online;
    }

    public static void setOnline(String online) {
        Swagger2Config.online = online;
    }

    @Bean
    public Docket createRestApi() {

        java.util.List<ResponseMessage> resMsgList = Arrays.asList(
                new ResponseMessageBuilder().code(0).message("成功").build(),
                new ResponseMessageBuilder().code(1).message("失败").build());

        if("1".equals(online)){
            logger.debug("---online---"+online);
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfoOnline())
                    .select()
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.none())
                    .build()
                    ;
        }else{
            logger.debug("---online--else-"+online);
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    //.apis(RequestHandlerSelectors.basePackage("com.jzy.api.controller"))
                    //扫描所有有注解的api，用这种方式更灵活
                    .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                    .paths(PathSelectors.any())
                    .build()
                    .globalOperationParameters(
                            newArrayList(
                                    new ParameterBuilder()
                                            .name("apiEmpToken")
                                            .description("后台鉴权token")
                                            .modelRef(new ModelRef("string"))
                                            .parameterType("header")
                                            .required(true)
                                            .defaultValue("c2ea579f3f90258543e03352ea95b550")
                                            .build(),
                                    new ParameterBuilder()
                                            .name("apiUserToken")
                                            .description("前台鉴权token")
                                            .modelRef(new ModelRef("string"))
                                            .parameterType("header")
                                            .required(true)
                                            .defaultValue("uJHJ1CBJ2iUz28ew")
                                            .build(),
                                    new ParameterBuilder()
                                            .name("appType")
                                            .description("前后台1-前台 2-后台")
                                            .modelRef(new ModelRef("string"))
                                            .parameterType("header")
                                            .required(false)
                                            .defaultValue("2")
                                            .build()
                            )
                    )
                    .globalResponseMessage(RequestMethod.GET, resMsgList)
                    .globalResponseMessage(RequestMethod.POST, resMsgList)
                    .globalResponseMessage(RequestMethod.PUT, resMsgList)
                    .globalResponseMessage(RequestMethod.DELETE, resMsgList);
        }


    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("充值中心后台API接口文档")
                .description("API Document")
                .termsOfServiceUrl("http://www.jiazhiyi.com")
                .version("1.0")
                .build();
    }

    private ApiInfo apiInfoOnline() {
        return new ApiInfoBuilder()
                .title("")
                .description("")
                .termsOfServiceUrl("")
                .version("")
                .build();
    }




}
