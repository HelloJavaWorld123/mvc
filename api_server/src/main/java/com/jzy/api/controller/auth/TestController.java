package com.jzy.api.controller.auth;

import com.jzy.api.service.auth.impl.SysTableKeyServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private SysTableKeyServiceImpl sysTableKeyService;

    @ResponseBody
    @RequestMapping("/test1")
    public String test1() {
        sysTableKeyService.newkey("order", "id", 1000);
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
        sysTableKeyService.newkey("order", "id", 1000);
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
        sysTableKeyService.newkey("order", "id", 1000);
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
