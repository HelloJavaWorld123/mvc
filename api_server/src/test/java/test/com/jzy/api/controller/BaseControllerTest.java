package test.com.jzy.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import test.com.jzy.api.controller.app.AppTypeControllerTest;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by herr on 2019/5/17.
 * 所有继承BaseControllerTest的测试数据会自动回滚
 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/config/applicationContext.xml", "classpath:/config/spring-mvc.xml" })
@WebAppConfiguration
@Transactional
@Rollback
@Ignore
public class BaseControllerTest {

    protected static final String errMsg = "测试不通过";

    @Autowired
    WebApplicationContext wac;

    protected MockMvc mockMvc;

    protected HttpHeaders httpHeaders=new HttpHeaders();

    @Before
    public void before() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        httpHeaders.set("apiEmpToken","c2ea579f3f90258543e03352ea95b550");
        httpHeaders.set("appType","2");
    }

    @After
    public void after() throws Exception {
    }

    public BaseControllerTest(){

    }

    protected JSONObject get(String uri, Map<String, Object> params) throws Exception{

        String paramStr = buildParamsStr(params,false);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/appType/admin/getList"+paramStr).headers(httpHeaders)
        ).andReturn();

        JSONObject result = JSON.parseObject(mvcResult.getResponse().getContentAsString());

        int status = mvcResult.getResponse().getStatus();

        return result;
    }

    protected JSONObject post(String uri, Object body) throws Exception{

        String requestJson = JSONObject.toJSONString(body);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).headers(httpHeaders).content(requestJson)
        ).andReturn();

        JSONObject result = JSON.parseObject(mvcResult.getResponse().getContentAsString());

        return result;
    }


    private String buildParamsStr(Map<String, Object> param, boolean isBuildSign) {
        if (param.size() == 0) {
            return "";
        }

        SortedMap<String, Object> params = new TreeMap(param);
        StringBuilder stringBuilder = new StringBuilder();

        params.forEach((key, value) -> {
            if (isBuildSign) {
                if (value != null && StringUtils.isNotBlank(value.toString())) {
                    stringBuilder.append(key + "=" + "signvalue" + "&");
                }
            } else {
                stringBuilder.append(key + "=" + value.toString() + "&");
            }
        });

        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

}