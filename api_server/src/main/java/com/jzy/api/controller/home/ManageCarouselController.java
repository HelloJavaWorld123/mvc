package com.jzy.api.controller.home;

import com.jzy.api.cnd.home.*;
import com.jzy.api.service.home.DealerHomeCateService;
import com.jzy.api.vo.home.DealerAppCateVo;
import com.jzy.api.vo.home.DealerAppInfoVo;
import com.jzy.api.vo.home.DealerHomeCateVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：后台管理轮播图</b>
 * add by jiazk 2019.05.01
 */
@Api(tags = "渠道商管理-首页分组/轮播图")
@Controller
@ResponseBody
@RequestMapping("ManageCarousel")
public class ManageCarouselController {

    private final static Logger logger = LoggerFactory.getLogger(ManageCarouselController.class);

    @Resource
    private DealerHomeCateService dealerHomeCateService;

    @RequestMapping("index")
    @RequiresPermissions(value = "a:manageCarousel:index")
    @ApiOperation(httpMethod="POST" ,value = "列表分页查询")
    public ApiResult index(@RequestBody DealerHomeCateListCnd listCnd) {
        PageVo<DealerHomeCateVo> result;
        try {
            result = dealerHomeCateService.listForPage(listCnd);
        } catch (Exception e) {
            logger.error("渠道商轮播图列表异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(result);
    }

    @RequestMapping("getAppInfo")
    @ApiOperation(httpMethod="POST" ,value = "商品详情")
    public ApiResult getAppInfo(@RequestBody IdCnd idCnd) {
        DealerHomeCateVo detailVo;
        try {
            detailVo = dealerHomeCateService.getById(idCnd.getId());
        } catch (Exception e) {
            logger.error("admin-app_info_id={},获取基础商品详情异常:{}", idCnd.getId(), e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(detailVo);
    }

    @RequestMapping("save")
    @RequiresPermissions(value = "a:manageCarousel:save")
    @ApiOperation(httpMethod="POST" ,value = "保存")
    public ApiResult save(@RequestBody DealerHomeCateSaveCnd infoCnd) {
        ApiResult apiResult = null;
        try {
            apiResult = dealerHomeCateService.save(infoCnd);

        } catch (Exception e) {
            logger.error("admin添加轮播图异常:{}", e);
            apiResult = new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return apiResult;
    }

    @RequestMapping("updateStatusBatch")
    @RequiresPermissions(value = "a:manageCarousel:updateStatusBatch")
    @ApiOperation(httpMethod="POST" ,value = "更新状态")
    public ApiResult updateStatusBatch(@RequestBody CommonUpdateStatusCnd updateStatusBatchCnd) {
        dealerHomeCateService.updateStatusBatch(updateStatusBatchCnd);
        return new ApiResult<>();
    }

    @Transactional
    @RequestMapping("deleteBatch")
    @RequiresPermissions(value = "a:manageCarousel:deleteBatch")
    @ApiOperation(httpMethod="POST" ,value = "删除")
    public ApiResult deleteBatch(@RequestBody CommonDeleteBatchCnd commonDeleteBatchCnd) {
        List<Long> ids = commonDeleteBatchCnd.getIds();
        for (Long id : ids) {
            DealerHomeCateVo entity = dealerHomeCateService.getById(id);
            if (entity.getStatus() != 0) {
                throw new BusException("存在轮播图未禁用，不能进行批量删除！");
            }
        }
        dealerHomeCateService.deleteBatch(ids);
        return new ApiResult<>();
    }
    /** 渠道商轮播图获取商品分类
     * @Description
     * @Author lchl
     * @Date 2019/5/15 4:32 PM
     * @param
     * @return com.jzy.framework.result.ApiResult
     */
    @RequestMapping("getAppCate")
    @ApiOperation(httpMethod="GET" ,value = "商品分类")
    public ApiResult getAppCate(){
        GetDealerAppOrCateCnd getDealerAppOrCateCnd = new GetDealerAppOrCateCnd();
        List<DealerAppCateVo>  resultList = dealerHomeCateService.getAppCate(getDealerAppOrCateCnd);
        return new ApiResult<>(resultList);
    }
    /**渠道商轮播图获取商品信息列表
     * @Description
     * @Author lchl
     * @Date 2019/5/15 4:33 PM
     * @param getDealerAppOrCateCnd
     * @return com.jzy.framework.result.ApiResult
     */
    @RequestMapping("getDealerAppList")
    @ApiOperation(httpMethod="POST" ,value = "商品信息列表")
    public ApiResult getDealerAppList(@RequestBody GetDealerAppOrCateCnd getDealerAppOrCateCnd){

        List<DealerAppInfoVo>  resultList = dealerHomeCateService.getDealerAppList(getDealerAppOrCateCnd);
        return new ApiResult<>(resultList);
    }
    /**渠道商轮播图获取商品定价信息列表
     * @Description
     * @Author lchl
     * @Date 2019/5/16 3:27 PM
     * @param getDealerAppOrCateCnd
     * @return com.jzy.framework.result.ApiResult
     */
    @RequestMapping("getDealerAppPriceList")
    @ApiOperation(httpMethod="POST" ,value = "商品定价信息列表")
    public ApiResult getDealerAppPriceList(@RequestBody GetDealerAppOrCateCnd getDealerAppOrCateCnd){

        List<DealerAppInfoVo>  resultList = dealerHomeCateService.getDealerAppPriceList(getDealerAppOrCateCnd);
        return new ApiResult<>(resultList);
    }
}
