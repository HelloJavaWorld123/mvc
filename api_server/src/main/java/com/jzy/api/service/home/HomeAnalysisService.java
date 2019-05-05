package com.jzy.api.service.home;

import com.jzy.api.cnd.home.HomeAnalysisCnd;
import com.jzy.api.vo.home.HomeAnalysisInfoVo;
import com.jzy.framework.result.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <b>功能描述：</b>渠道商信息解析<br>
 * <b>修订记录：</b><br>
 * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
 */
public interface HomeAnalysisService {


    /**
     * <b>功能描述：</b>解析加密信息返回给前端<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    HomeAnalysisInfoVo getInfo(HomeAnalysisCnd homeAnalysisCnd);


}
