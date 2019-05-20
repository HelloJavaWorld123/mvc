package com.jzy.api.cnd.arch;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>渠道商面值信息传值(渠道商商品信息保存)<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190514&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAppPriceInfoCnd {
    /**
     * 主键
     */
    private Long id;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 支付金额
     */
    private BigDecimal payPrice = BigDecimal.ZERO;

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
    private BigDecimal discount = BigDecimal.ZERO;


    /**
     * 渠道商价
     */
    private BigDecimal dealerPrice;


    /**
     * 充值提示信息
     */
    private String tips;

    /**
     * 备注
     */
    private String remark;


    /**
     * 渠道商主键
     */
    private String dealerId;

    /**
     * 状态 0 下架 1上架  默认0
     */
    private Integer status = 0;


}
