package com.jzy.api.vo.home;

import com.jzy.api.po.arch.DealerParamInfoPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>渠道商信息信息返回<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HomeAnalysisInfoVo {


    /**
     * 渠道商标识
     */
    private String businessId;


    /**
     * 买家信息
     */
    private String userId;

    /**
     * 把签名作为token返回给前端
     */
    private String token;


    private List<DealerParamInfoPo> dealerParamInfoPos = new ArrayList<>(10);


}
