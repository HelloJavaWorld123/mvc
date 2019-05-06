package com.jzy.api.controller.home;

import com.jzy.api.cnd.home.HomeAnalysisCnd;
import com.jzy.api.service.home.HomeAnalysisService;
import com.jzy.api.vo.home.HomeAnalysisInfoVo;
import com.jzy.framework.result.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <b>功能：</b>首页渠道商token信息解密<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("analysis")
public class HomeAnalysisController {


    @Resource
    private HomeAnalysisService homeAnalysisService;

    /**
     * <b>功能描述：</b>解析加密信息返回给前端<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getinfo")
    public ApiResult getInfo(@RequestBody HomeAnalysisCnd homeAnalysisCnd) {
        HomeAnalysisInfoVo homeAnalysisInfoVo = homeAnalysisService.getInfo(homeAnalysisCnd);
        if (null == homeAnalysisInfoVo) {
            return new ApiResult().fail("信息错误请检查");
        }
        return new ApiResult<>(homeAnalysisInfoVo);
    }


}
