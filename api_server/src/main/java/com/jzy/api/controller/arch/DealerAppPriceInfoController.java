package com.jzy.api.controller.arch;

import com.jzy.api.cnd.app.*;
import com.jzy.api.cnd.arch.*;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.service.arch.DealerAppPriceInfoService;
import com.jzy.api.vo.app.AppDetailVo;
import com.jzy.api.vo.dealer.DealerAppPriceInfoDetailVo;
import com.jzy.api.vo.dealer.GetDealerAppVo;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.ExcelException;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：</b>渠道商商品定价<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("dealAppPriceInfo")
@Api(tags = "渠道商商品定价")
public class DealerAppPriceInfoController {


    @Resource
    private DealerAppPriceInfoService dealerAppPriceInfoService;

    /**
     * <b>功能描述：</b>前台查询商品价格接口<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getPrice")
    @ApiOperation(httpMethod="POST" ,value = "前端-查询商品价格")
    public ApiResult getPrice(@RequestBody GetPriceCnd getPriceCnd) {
        List<DealerAppPriceInfoPo> dealerAppPriceInfoPoList = dealerAppPriceInfoService.getPrice(getPriceCnd);
        return new ApiResult<>(dealerAppPriceInfoPoList);
    }

    /**
     * <b>功能描述：</b>前台查询商品详情<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getAppDetail")
    @ApiOperation(httpMethod="POST" ,value = "前端-商品详情")
    public ApiResult getAppDetail(@RequestBody IdCnd idCnd) {
        AppDetailVo appDetail = dealerAppPriceInfoService.getAppDetail(idCnd.getId().toString());
        return new ApiResult<>(appDetail);
    }

    /**
     * <b>功能描述：</b>渠道商商品热门搜索<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("appSearchList")
    @ApiOperation(httpMethod="POST" ,value = "前端-渠道商商品热门搜索")
    public ApiResult appSearchList(@RequestBody AppSearchListCnd appSearchListCnd) {
        PageVo pageInfo = dealerAppPriceInfoService.appSearchList(appSearchListCnd);
        return new ApiResult<>(pageInfo);
    }

    /**
     * <b>功能描述：</b>查询渠道商下对应的商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/getList")
    @RequiresPermissions(value = "a:dealAppPriceInfo:getList")
    @ApiOperation(httpMethod="POST" ,value = "查询渠道商下对应的商品列表")
    public ApiResult getList(@RequestBody GetDealerAppListCnd getDealerAppListCnd) {
        PageVo pageVo = dealerAppPriceInfoService.getList(getDealerAppListCnd);
        return new ApiResult<>(pageVo);
    }


    /**
     * <b>功能描述：</b>查询渠道商商品定价详情<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/getDealerAppDetail")
    @RequiresPermissions(value = "a:dealAppPriceInfo:getDealerAppDetail")
    @ApiOperation(httpMethod="POST" ,value = "渠道商查询商品配价列表")
    public ApiResult getDealerAppDetail(@RequestBody GetPriceInfoCnd getPriceInfoCnd) {
        DealerAppPriceInfoDetailVo dealerAppPriceInfoDetailVo = dealerAppPriceInfoService.getDealerAppDetail(getPriceInfoCnd);
        return new ApiResult<>(dealerAppPriceInfoDetailVo);
    }

    /**
     * <b>功能描述：</b>渠道商商品定价信息的保存<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    @RequestMapping("admin/save")
    @RequiresPermissions(value = "a:dealAppPriceInfo:save")
    @ApiOperation(httpMethod="POST" ,value = "渠道商商品配价保存更新")
    public ApiResult save(@RequestBody SavePriceInfoCnd savePriceInfoCnd) {
        dealerAppPriceInfoService.save(savePriceInfoCnd);
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>批量修改上下架状态<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/batchUpdateStatus")
    @RequiresPermissions(value = "a:dealAppPriceInfo:batchUpdateStatus")
    @ApiOperation(httpMethod="POST" ,value = "渠道商商品批量上下架")
    public ApiResult batchUpdateStatus(@RequestBody BatchUpdateStatusCnd batchUpdateStatusCnd) {
        dealerAppPriceInfoService.updateStatus(batchUpdateStatusCnd);
        return new ApiResult<>();
    }
}
