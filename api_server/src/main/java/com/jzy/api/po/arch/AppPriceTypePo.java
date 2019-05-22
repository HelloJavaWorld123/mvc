package com.jzy.api.po.arch;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>商品充值类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppPriceTypePo {
    private String typeId;         // 商品充值类型 - 类型ID：aptId
    private String typeName;         // 商品充值类型 - 名称
    private String typeUnit;        // 商品充值类型 - 单位
    private BigDecimal maxMum;          // 商品充值类型 - 最大充值面值
    private BigDecimal minMum;         // 商品充值类型 - 最小充值面值
    private BigDecimal multiple;          // 商品充值类型 - 充值倍数
    private String subscriptionRatio;   // 商品充值类型 - 1元兑换比例
    /**
     * 是否允许用户输入自定义金额
     */
    private Boolean isCustom;
}
