package com.jzy.api.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @RequestMapping("/test1")
    public String test1() {

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
