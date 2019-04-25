package com.jzy.api.controller;

import com.google.gson.Gson;

import com.jzy.api.base.JsonContentTypeView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping(path="/user")
public class UserController {

    @RequestMapping(path="/index")
    public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/views/login";
    }

    @RequestMapping(path="/mainPage")
    public String main(HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/main";
    }

    @RequestMapping(path="/login1")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        Gson gson = new Gson();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(!"root".equals(username)) {
            model.put("result","fail");
        }
        if(!"root".equals(password)) {
            model.put("result","fail");
        }
        model.put("result","success");
        return new ModelAndView(new JsonContentTypeView(gson.toJson(model)));
    }
}
