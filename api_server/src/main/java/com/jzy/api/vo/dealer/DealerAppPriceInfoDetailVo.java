package com.jzy.api.vo.dealer;

import com.jzy.api.po.dealer.DealerAppTypePriceInfoPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>查询渠道商商品定价详情返回参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAppPriceInfoDetailVo {


    /**
     * 商品编号
     */
    private String appCode;


    /**
     * 商品名称
     */
    private String appName;


    /**
     * 渠道商充值类型面值信息
     */
    private List<DealerAppTypePriceInfoPo> dealerAppTypePriceInfoList = new ArrayList<>(10);


}
