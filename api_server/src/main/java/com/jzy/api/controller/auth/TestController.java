package com.jzy.api.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @ResponseBody
    @RequestMapping("/v1")
    public String testLogin() {

        return "error/page500";
    }

    @ResponseBody
    @RequestMapping("/add")
    public String add() {

        return "error/add";
    }


}
