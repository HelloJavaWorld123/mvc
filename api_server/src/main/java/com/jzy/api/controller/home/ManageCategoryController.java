package com.jzy.api.controller.home;

import com.jzy.api.service.home.DealerHomeCateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@ResponseBody
@RequestMapping("admin/dealer/home/category")
public class ManageCategoryController {

    @Resource
    private DealerHomeCateService dealerHomeCateService;

}
