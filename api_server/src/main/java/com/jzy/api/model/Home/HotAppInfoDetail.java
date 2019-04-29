package com.jzy.api.model.Home;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>推荐商品商品详情<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HotAppInfoDetail {

    /**
     * 商品充值类型名称
     */
    private String typeName;

    /**
     * 商品充值类型单位
     */
    private String typeUnit;

    /**
     * 金额
     */
    private BigDecimal price;


    /**
     * 支付金额
     */
    private BigDecimal payPrice;


    /**
     * 折扣
     */
    private BigDecimal discount;


}
