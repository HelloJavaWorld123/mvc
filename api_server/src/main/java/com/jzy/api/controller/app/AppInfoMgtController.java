package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.result.ApiResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 应用controller
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Controller
@ResponseBody
@RequestMapping("admin/appinfo")
public class AppInfoMgtController {

    private final static Logger logger = LoggerFactory.getLogger(AppInfoMgtController.class);

//    @Resource
//    private AppPriceTypeService appPriceTypeService;
//
//    @Resource
//    private SysImagesService sysImagesService;

    @Resource
    private AppInfoService appInfoService;
//    @Resource
//    private IMongoService iMongoService;


    /**
     * <b>功能描述：</b>商品列表分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("index.shtml")
    public ApiResult index(@RequestBody AppInfoListCnd appInfoListCnd) {

        List<AppInfoListVo> appInfoList;
        try {
            appInfoList = appInfoService.listPage(appInfoListCnd);
        } catch (Exception e) {
            logger.error("admin产品列表异常:{}", e);
            return new ApiResult(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>(appInfoList);
    }
    
    /**
     * <b>功能描述：</b>获取商品详情<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("get_info.shtml")
    public ApiResult getAppInfo(@RequestBody IdCnd idCnd) {
        AppInfoDetailVo appInfoDetailVo;
        try {
            appInfoDetailVo = appInfoService.getAppInfo(idCnd.getId());
        } catch (Exception e) {
            logger.error("admin-app_info_id={},获取基础商品详情异常:{}", idCnd.getId(), e);
            return new ApiResult(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>(appInfoDetailVo);
    }



}
