package com.jzy.api.controller.home;

import com.jzy.api.cnd.home.HomeRecommendHotListCnd;
import com.jzy.api.service.home.HomeRecommendCateService;
import com.jzy.api.vo.home.HomeRecommendCateVo;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("HomeRecommendCate")
@Api(tags = "前端-首页推荐")
public class HomeRecommendCateController {

    // @Resource
    //private DealerService iDealerService;

    @Resource
    private HomeRecommendCateService homeRecommendCateService;

    /**
     * <b>功能描述：</b>渠道商首页推荐轮播图和推荐分类<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "getList")
    @ApiOperation(httpMethod="POST" ,value = "推荐轮播图和推荐分类")
    public ApiResult getList(@RequestBody HomeRecommendHotListCnd homeRecommendHotListCnd) {

        Integer type = homeRecommendHotListCnd.getType();
        List<HomeRecommendCateVo> homeRecommendHotVoList = homeRecommendCateService.getList(type);
        return new ApiResult<>(homeRecommendHotVoList);
    }


}
