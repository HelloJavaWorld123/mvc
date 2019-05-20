package com.jzy.api.po.arch;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>前台查询商品价格接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAppPriceInfoPo {

    /**
     * 面值表主键
     */
    private String id;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 支付金额
     */
    private BigDecimal payPrice;

    /**
     * sup价格
     */
    private BigDecimal supPrice;


    /**
     * sup编号
     */
    private String supNo;


    /**
     * 数量
     */

    private Integer number;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 渠道商价
     */
    private BigDecimal dealerPrice;


    /**
     * 状态
     */
    private Integer status;

}
