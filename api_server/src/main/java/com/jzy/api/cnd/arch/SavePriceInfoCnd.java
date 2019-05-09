package com.jzy.api.cnd.arch;

import com.jzy.api.model.dealer.DealerAppPriceInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>保存渠道商定价信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SavePriceInfoCnd {

    /**
     * 渠道商主键
     */
    private String dealerId;

    /**
     * 商品表主键
     */
    private String aiId;

    private List<DealerAppPriceInfo> dealerAppPriceInfoMapperList = new ArrayList<>(10);

}
