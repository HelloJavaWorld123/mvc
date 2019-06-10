package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.AppTypeCnd;
import com.jzy.api.cnd.app.AppTypeListCnd;
import com.jzy.api.model.app.AppType;
import com.jzy.api.po.app.AppTypePo;
import com.jzy.api.service.app.AppTypeService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.vo.app.AppTypeVo;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.jzy.common.enums.ResultEnum.OPERATION_FAILED;


/**
 * <b>功能：</b>产品类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("appType")
public class AppTypeController {

    private final static Logger logger = LoggerFactory.getLogger(AppTypeController.class);

    @Resource
    private AppTypeService appTypeService;

    @Resource
    private TableKeyService tableKeyService;


    /**
     * <b>功能描述：</b>产品类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/getList")
    @RequiresPermissions(value = {"a:appType:list"})
    public ApiResult getList() {
        List<AppTypePo> appTypeList;
        try {
            appTypeList = appTypeService.getList();
        } catch (Exception e) {
            logger.error("admin产品类型异常:{}", e);
            return new ApiResult(OPERATION_FAILED);
        }
        return new ApiResult<>(appTypeList);
    }

    /**
     * <b>功能描述：</b>产品类型分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/index")
    @RequiresPermissions(value = "a:appType:index")
    public ApiResult index(@RequestBody AppTypeListCnd appTypeListCnd) {
        PageVo<AppTypeVo> result;
        try {
            result = appTypeService.listPage(appTypeListCnd);
        } catch (Exception e) {
            logger.error("admin产品类型分页查询异常:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>(result);
    }

    /**
     * <b>功能描述：</b>添加产品类型<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/save")
    @RequiresPermissions(value = "a:appType:save")
    public ApiResult save(@RequestBody AppTypeCnd appTypeCnd) {
        try {
            AppType appType = new AppType();
            BeanUtils.copyProperties(appTypeCnd,appType);
            appType.setId(tableKeyService.newKey("app_type", "id", 0));
            //默认排序1000
            appType.setSort(1000);
            appTypeService.save(appType);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin产品类型添加:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>商品类型删除，物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/delete")
    @RequiresPermissions(value = "a:appType:delete")
    public ApiResult deleteBatch(@RequestBody IdCnd idCnd) {
        try {
            appTypeService.delete(idCnd.getId());
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin产品类型删除:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }
}
