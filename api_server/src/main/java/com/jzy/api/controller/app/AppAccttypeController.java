package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.AppAccttypeCnd;
import com.jzy.api.cnd.app.AppAccttypeListCnd;
import com.jzy.api.model.app.AppAccttype;
import com.jzy.api.po.app.AppAccttypeListPo;
import com.jzy.api.service.app.AppAccttypeService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.vo.app.AppAccttypeVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.jzy.common.enums.ResultEnum.OPERATION_FAILED;


/**
 * 账号类型controller
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Controller
@ResponseBody
@RequestMapping("appAccttype")
@Api(tags="账号类型")
public class AppAccttypeController {

    private final static Logger logger = LoggerFactory.getLogger(AppAccttypeController.class);

    @Resource
    private AppAccttypeService appAccttypeService;

    @Resource
    private TableKeyService tableKeyService;


  /**
   * <b>功能描述：</b>账号列表查询<br>
   * <b>修订记录：</b><br>
   * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
   */
    @RequestMapping("admin/list")
    @RequiresPermissions(value = {"a:appAcctType:list"})
    @ApiOperation(httpMethod="POST" ,value = "列表查询")
    public ApiResult list() {
        List<AppAccttypeListPo> acctList = new ArrayList<>();
        try {
            acctList =appAccttypeService.list();
        } catch (Exception e) {
            logger.error("admin账号类型列表异常:{}", e);
        }
        return new ApiResult<>(acctList);
    }

    /**
     * <b>功能描述：</b>账号类型分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/index")
    @RequiresPermissions(value = "a:appAcctType:index")
    @ApiOperation(httpMethod="POST" ,value = "分页查询")
    public ApiResult index(@RequestBody AppAccttypeListCnd appAccttypeListCnd) {
        PageVo<AppAccttypeVo> result;
        try {
            result = appAccttypeService.listPage(appAccttypeListCnd);
        } catch (Exception e) {
            logger.error("admin账号类别列表异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(result);
    }

    /**
     * <b>功能描述：</b>添加账号类型<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/save")
    @RequiresPermissions(value = "a:appAcctType:save")
    @ApiOperation(httpMethod="POST" ,value = "保存")
    public ApiResult save(@RequestBody AppAccttypeCnd appAccttypeCnd) {
        try {
            AppAccttype appAccttype = new AppAccttype();
            BeanUtils.copyProperties(appAccttypeCnd,appAccttype);
            appAccttype.setId(tableKeyService.newKey("app_accttype", "id", 0));
            appAccttypeService.save(appAccttype);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin账号类型添加:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>账号类型删除，物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/delete")
    @RequiresPermissions(value = "a:appAcctType:delete")
    @ApiOperation(httpMethod="POST" ,value = "删除")
    public ApiResult deleteBatch(@RequestBody IdCnd idCnd) {
        try {
            appAccttypeService.delete(idCnd.getId());
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin账号类型删除:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>编辑厂商<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/update")
    @RequiresPermissions(value = "a:appAcctType:update")
    @ApiOperation(httpMethod="POST" ,value = "更新")
    public ApiResult update(@RequestBody AppAccttypeCnd appAccttypeCnd) {
        try {
            AppAccttype appAccttype = new AppAccttype();
            BeanUtils.copyProperties(appAccttypeCnd,appAccttype);
            appAccttype.setId(appAccttypeCnd.getId());
            appAccttypeService.edit(appAccttype);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin账号类型编辑:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }
}
