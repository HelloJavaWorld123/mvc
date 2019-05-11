package com.jzy.api.controller.home;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.home.HomeAnalysisCnd;
import com.jzy.api.cnd.home.HomeAuthCnd;
import com.jzy.api.po.arch.DataInfo;
import com.jzy.api.service.home.HomeAnalysisService;
import com.jzy.api.util.DesUtil;
import com.jzy.api.util.MyEncrypt;
import com.jzy.api.vo.home.HomeAnalysisInfoVo;
import com.jzy.framework.result.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @WithoutLogin
    @RequestMapping("getinfo")
    public ApiResult getInfo(@RequestBody HomeAnalysisCnd homeAnalysisCnd) {
        HomeAnalysisInfoVo homeAnalysisInfoVo = homeAnalysisService.update(homeAnalysisCnd);
        if (null == homeAnalysisInfoVo) {
            return new ApiResult().fail("信息错误请检查");
        }
        return new ApiResult<>(homeAnalysisInfoVo);
    }


    /**
     * <b>功能描述：</b>加密渠道商信息返回给前端<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;贾昭凯&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @WithoutLogin
    @RequestMapping("getauth")
    public ApiResult getauth(@RequestBody HomeAuthCnd homeAuthCnd) {
        String resultData = homeAnalysisService.getauth(homeAuthCnd);

//        String des3Decrypt = DesUtil.des3Decrypt("Kxl9nogO7Zixn4UMWtAu7A==", "10c17b42b5b94c4e93cd574b6e37aeeb", "utf-8");

        return new ApiResult<>(resultData);
    }
}
