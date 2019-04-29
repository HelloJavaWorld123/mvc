package com.jzy.api.controller.home;

import com.jzy.api.service.home.HomeRecommendHotService;
import com.jzy.api.vo.home.HomeRecommendHotVo;
import com.jzy.framework.result.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("admin/home/recommend/hot")
public class HomeRecommendHotController {

    //@Resource
   // private IDealerService iDealerService;

    @Resource
    private HomeRecommendHotService homeRecommendHotService;

    /**
     * <b>功能描述：</b>首页查询商品推荐列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("list.shtml")
    public ApiResult getList(HttpServletRequest request) {
        //Integer dealerId = iDealerService.queryByUseridOrDefault(request.getSession()).getId();
        List<HomeRecommendHotVo> homeRecommendHotVoList = homeRecommendHotService.getList("1001");
        return new ApiResult<>(homeRecommendHotVoList);
    }


}
