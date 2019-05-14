package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.AppCompanyCnd;
import com.jzy.api.model.app.AppCompany;
import com.jzy.api.po.app.AppCompanyPo;
import com.jzy.api.service.app.AppCompanyService;
import com.jzy.api.vo.app.AppCompanyVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * <b>功能描述：</b>厂商列表分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/index")
    public ApiResult index(@RequestBody AppCompanyCnd appCompanyCnd) {
        PageVo<AppCompanyVo> result;
        try {
            result = appCompanyService.listPage(appCompanyCnd);
        } catch (Exception e) {
            logger.error("admin厂商列表异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(result);
    }

    /**
     * <b>功能描述：</b>添加厂商<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/save")
    public ApiResult save(@RequestBody AppCompanyCnd appCompanyCnd) {
        try {
            AppCompany appCompany = new AppCompany();
            BeanUtils.copyProperties(appCompanyCnd,appCompany);
            appCompanyService.save(appCompany);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin厂商添加:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>厂商删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/delete")
    public ApiResult deleteBatch(@RequestBody IdCnd idCnd) {
        try {
            appCompanyService.delete(idCnd.getId());
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin厂商删除:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>编辑厂商<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/update")
    public ApiResult update(@RequestBody AppCompanyCnd appCompanyCnd) {
        try {
            AppCompany appCompany = new AppCompany();
            BeanUtils.copyProperties(appCompanyCnd,appCompany);
            appCompany.setId(appCompanyCnd.getId());
            appCompanyService.edit(appCompany);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin厂商编辑:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }
}
