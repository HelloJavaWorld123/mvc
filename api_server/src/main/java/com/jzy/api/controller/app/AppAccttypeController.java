package com.jzy.api.controller.app;

import com.jzy.api.model.app.AppAccttype;
import com.jzy.api.service.app.AppAccttypeService;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 账号类型controller
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Controller
@ResponseBody
@RequestMapping(" appAccttype")
public class AppAccttypeController {

    private final static Logger logger = LoggerFactory.getLogger(AppAccttypeController.class);

    @Resource
    private AppAccttypeService appAccttypeService;


  /**
   * <b>功能描述：</b>账号列表查询<br>
   * <b>修订记录：</b><br>
   * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
   */
    @RequestMapping("admin/list")
    public ApiResult list() {
        List<AppAccttype> acctList = new ArrayList<>();
        try {
            acctList =appAccttypeService.list();
        } catch (Exception e) {
            logger.error("admin账号类型列表异常:{}", e);
        }
        return new ApiResult<>(acctList);
    }




}
