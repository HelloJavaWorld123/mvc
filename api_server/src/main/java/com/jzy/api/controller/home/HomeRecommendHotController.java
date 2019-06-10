package com.jzy.api.controller.home;

import com.jzy.api.cnd.home.DialogBannerCnd;
import com.jzy.api.cnd.home.HomeHotListCnd;
import com.jzy.api.cnd.home.HomeRecommendHotCnd;
import com.jzy.api.cnd.home.HomeRecommendHotGroupCnd;
import com.jzy.api.model.Home.HomeRecommendHot;
import com.jzy.api.model.Home.HomeRecommendHotDetail;
import com.jzy.api.model.Home.HomeRecommendHotGroup;
import com.jzy.api.service.arch.DealerAppPriceInfoService;
import com.jzy.api.service.home.HomeRecommendHotService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.vo.app.AppDetailVo;
import com.jzy.api.vo.home.DialogBannerVo;
import com.jzy.api.vo.home.HomeHotInfoVo;
import com.jzy.api.vo.home.HomeHotVo;
import com.jzy.api.vo.home.HomeRecommendHotVo;
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
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <b>功能：</b>渠道商首页推荐Hot<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("HomeRecommendHot")
public class HomeRecommendHotController {

    private final static Logger logger = LoggerFactory.getLogger(HomeRecommendHotController.class);

    //@Resource
   // private IDealerService iDealerService;

    @Resource
    private HomeRecommendHotService homeRecommendHotService;

    @Resource
    private DealerAppPriceInfoService dealerAppPriceInfoService;

    /**
     * <b>功能描述：</b>首页查询商品推荐列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getList")
    public ApiResult getList() {
        List<HomeRecommendHotVo> homeRecommendHotVoList = homeRecommendHotService.getList();
        return new ApiResult<>(homeRecommendHotVoList);
    }

    /**
     * <b>功能描述：</b>首页推荐商品列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/index")
    public ApiResult index(@RequestBody HomeHotListCnd homeHotListCnd) {
        PageVo<HomeHotVo> result;
        try {
            result = homeRecommendHotService.listPage(homeHotListCnd);
        } catch (Exception e) {
            logger.error("admin首页推荐分组商品列表:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(result);
    }

    /**
     * <b>功能描述：</b>获取分组商品详情<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/getHomeHot")
    public ApiResult getHomeHot(@RequestBody IdCnd idCnd) {
        HomeHotInfoVo homeHotInfoVo;
        try {
            homeHotInfoVo = homeRecommendHotService.getHomeInfoHot(idCnd.getId());
        } catch (Exception e) {
            logger.error("获取首页推荐分组商品信息异常:{}", idCnd.getId(), e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(homeHotInfoVo);
    }

    /**
     * <b>功能描述：</b>首页推荐商品编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/update")
    public ApiResult update(@RequestBody HomeRecommendHotCnd homeRecommendHotCnd) {
        try {
            homeRecommendHotService.edit(homeRecommendHotCnd);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin首页推荐商品更新失败:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>618活动弹出和banner<br>
     * <b>修订记录：</b><br>
     * <li>20190604&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getDialogAndBanner")
    public ApiResult getDialogAndBanner(@RequestBody DialogBannerCnd dialogBannerCnd) {
        //默认dialog和banner显示
        boolean dialogBanner=true;
        //判断商品id是否有值
        if(dialogBannerCnd.getAiId()!=null&&!dialogBannerCnd.getAiId().equals("")){
            AppDetailVo appDetail = dealerAppPriceInfoService.getAppDetail(dialogBannerCnd.getAiId());
            if(appDetail.getAppDetailPoList().size()==0){
                dialogBanner=false;
            }
        }
        List<HomeRecommendHotDetail> dialogBannerVoList = null;
        if(dialogBanner){
            //如果商品没有下架，可以显示banner和dialog
            dialogBannerVoList = homeRecommendHotService.getDialogBanner(dialogBannerCnd);

        }
        return new ApiResult<>(dialogBannerVoList);
    }

    /**
     * <b>功能描述：</b>猜你喜欢商品<br>
     * <b>修订记录：</b><br>
     * <li>20190604&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getLikeAppInfo")
    public ApiResult getLikeAppInfo(@RequestBody IdCnd idCnd) {
        List<HomeRecommendHotDetail> dialogBannerVoList = homeRecommendHotService.getLikeAppInfo(idCnd);
        return new ApiResult<>(dialogBannerVoList);
    }
}
