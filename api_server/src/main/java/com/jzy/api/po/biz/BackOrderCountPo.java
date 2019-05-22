package com.jzy.api.po.biz;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>订单统计<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190522&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class BackOrderCountPo {
    /**
     * 总面值
     */
    private BigDecimal priceTotal = BigDecimal.ZERO;
    /**
     * 应付总金额
     */
    private BigDecimal totalFeeTotal = BigDecimal.ZERO;
    /**
     * 实付总金额
     */
    private BigDecimal tradeFeeTotal = BigDecimal.ZERO;
    /**
     * 渠道商成本总金额
     */
    private BigDecimal dealerPriceTotal = BigDecimal.ZERO;
    /**
     * 渠道商利润总金额
     */
    private BigDecimal merchantProfitTotal = BigDecimal.ZERO;
}
