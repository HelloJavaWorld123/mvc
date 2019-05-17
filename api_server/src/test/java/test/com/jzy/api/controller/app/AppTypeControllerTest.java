package test.com.jzy.api.controller.app; 

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.jzy.api.cnd.app.AppTypeCnd;
import com.jzy.api.cnd.app.AppTypeListCnd;
import com.jzy.framework.bean.cnd.IdCnd;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.com.jzy.api.controller.BaseControllerTest;
import org.junit.Test;

import java.util.Map;

/** 
* AppTypeController Tester.
* 
* @author <herongrong>
* @since <pre>五月 17, 2019</pre> 
* @version 1.0 
*/
public class AppTypeControllerTest extends BaseControllerTest{


    private final static Logger logger = LoggerFactory.getLogger(AppTypeControllerTest.class);
/** 
* 
* Method: getList() 
*
*/ 
@Test
public void testGetList() throws Exception {

//    Map<String, Object> map = ImmutableMap.of("param1", "value1"
//            , "param2", "value2");

    Map<String, Object> map = ImmutableMap.of();

    JSONObject result = get("/appType/admin/getList",map);
    logger.info("result========>"+result);
//    Assert.assertEquals("请求失败", 200, status);
    Assert.assertEquals(errMsg,result.getIntValue("code"),1);
} 

/** 
* 
* Method: index(@RequestBody AppTypeListCnd appTypeListCnd) 
* 
*/ 
@Test
public void testIndex() throws Exception {
    AppTypeListCnd appTypeListCnd=new AppTypeListCnd();
    appTypeListCnd.setPage(1);
    appTypeListCnd.setLimit(10);

    JSONObject result = post("/appType/admin/index",appTypeListCnd);
    logger.info("result========>"+result);
    Assert.assertEquals(errMsg,result.getIntValue("code"),1);
} 

/** 
* 
* Method: save(@RequestBody AppTypeCnd appTypeCnd) 
* 
*/ 
@Test
public void testSave() throws Exception {
    AppTypeCnd appTypeCnd=new AppTypeCnd();
    appTypeCnd.setName("测试1");
    appTypeCnd.setSort(1000);

    JSONObject result = post("/appType/admin/save",appTypeCnd);
    logger.info("result========>"+result);
    Assert.assertEquals(errMsg,result.getIntValue("code"),1);
} 

/** 
* 
* Method: deleteBatch(@RequestBody IdCnd idCnd) 
* 
*/ 
@Test
public void testDeleteBatch() throws Exception {
    IdCnd idCnd=new IdCnd();
    idCnd.setId(10000L);

    JSONObject result = post("/appType/admin/delete",idCnd);
    logger.info("result========>"+result);
    Assert.assertEquals(errMsg,result.getIntValue("code"),1);
} 

/** 
* 
* Method: update(@RequestBody AppTypeCnd appTypeCnd) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    AppTypeCnd appTypeCnd=new AppTypeCnd();
    appTypeCnd.setId(10000L);
    appTypeCnd.setName("测试x");
    appTypeCnd.setSort(1001);

    JSONObject result = post("/appType/admin/update",appTypeCnd);
    logger.info("result========>"+result);
    Assert.assertEquals(errMsg,result.getIntValue("code"),1);
} 


} 
