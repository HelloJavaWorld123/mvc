package com.jzy.api.controller.auth;

import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.key.impl.TableKeyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private TableKeyService tableKeyService;

    @ResponseBody
    @RequestMapping("/test1")
    public String test1() {
        tableKeyService.newKey("order", "id", 1000);
        return "test/test1";
    }

    @ResponseBody
    @RequestMapping("/test2")
    public String test2() {

        return "test/test2";
    }

    @ResponseBody
    @RequestMapping("/test3")
    public String testLogin() {

        return "test/test3";
    }

    @ResponseBody
    @RequestMapping("/test4")
    public String test4() {
        for (int i =0; i< 10000; i++) {
            for (int j =0; j< 10000; j++) {
                tableKeyService.newKey("order_test1", "id", 1000);
                tableKeyService.newKey("order_test2", "id", 1000);
                tableKeyService.newKey("order_test3", "id", 1000);
                tableKeyService.newKey("order_test4", "id", 1000);
                tableKeyService.newKey("order_test5", "id", 1000);
                tableKeyService.newKey("order_test6", "id", 1000);
                tableKeyService.newKey("order_test7", "id", 1000);
                tableKeyService.newKey("order_test8", "id", 1000);
                tableKeyService.newKey("order_test9", "id", 1000);
                tableKeyService.newKey("order_test0", "id", 1000);
                tableKeyService.newKey("order_test11", "id", 1000);
                tableKeyService.newKey("order_test12", "id", 1000);
                tableKeyService.newKey("order_test13", "id", 1000);
            }
            log.debug("sysTableKeyService i =  " + i);
        }


        return "test/test4";
    }

    @ResponseBody
    @RequestMapping("/test5")
    public String test5() {

        return "test/test5";
    }

    @ResponseBody
    @RequestMapping("/test6")
    public String test6() {

        return "test/test6";
    }

    @ResponseBody
    @RequestMapping("/test7")
    public String test7() {

        return "test/test7";
    }

    @ResponseBody
    @RequestMapping("/test8")
    public String test8() {

        return "test/test8";
    }

    @ResponseBody
    @RequestMapping("/test9")
    public String test9() {

        return "test/test9";
    }
}
