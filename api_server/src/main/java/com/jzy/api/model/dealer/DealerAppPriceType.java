package com.jzy.api.model.dealer;

import lombok.Data;

/**
 * <b>功能：</b>渠道商关联商品充值类型表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */

@Data
public class DealerAppPriceType {
    /**
     * 主键
     */
    private String id;

    /**
     * 商品表主键
     */
    private String aiId;
    /**
     * 商品充值类型主键
     */

    private String AptId;

    /**
     * 渠道商主键
     */
    private String dealerId;

    /**
     * 是否自定义金额
     */
    private Integer isCustom;
}
