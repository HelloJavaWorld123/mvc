package com.jzy.api.controller.app;

import com.jzy.api.po.app.AppCompanyPo;
import com.jzy.api.service.app.AppCompanyService;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


/**
 * 厂商controller
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@ResponseBody
@RequestMapping("appCompany")
@Controller
public class AppCompanyController {

    private final static Logger logger = LoggerFactory.getLogger(AppCompanyController.class);

    @Resource
    private AppCompanyService appCompanyService;

    /**
     * <b>功能描述：</b>厂商列表(不带分页)<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/list")
    public ApiResult getList() {
        List<AppCompanyPo> appCompanyMapperList;
        try {
            appCompanyMapperList = appCompanyService.getList();
        } catch (Exception e) {
            logger.error("admin厂商列表异常:{}", e);
            return new ApiResult(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(appCompanyMapperList);
    }


}
