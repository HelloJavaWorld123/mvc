package com.jzy.api.po.dealer;


import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>渠道商充值类型面值信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAppTypePriceInfoPo {
    /**
     * 充值类型名称
     */
    private String typeName;


    /**
     * 充值类型Id
     */
    private String aptId;

    /**
     * 充值面值列表
     */
    private List<DealerAppPriceInfoPo> dealerAppPriceInfoPoList = new ArrayList<>(10);

}
