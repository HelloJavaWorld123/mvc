package com.jzy.api.cnd.arch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="渠道商商品配价信息")
public class DealerAppPriceInfoCnd {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 金额
     */
    @ApiModelProperty(value = "面值")
    private BigDecimal price;

    /**
     * 支付金额
     */
    @ApiModelProperty(value = "支付金额")
    private BigDecimal payPrice = BigDecimal.ZERO;

    /**
     * sup价格
     */
    @ApiModelProperty(value = "sup价格")
    private BigDecimal supPrice;


    /**
     * sup编号
     */
    @ApiModelProperty(value = "sup编号")
    private String supNo;


    /**
     * 数量
     */
    @ApiModelProperty(value = "充值数量")
    private Integer number;


    /**
     * 折扣
     */
    @ApiModelProperty(value = "折扣")
    private BigDecimal discount = BigDecimal.ZERO;


    /**
     * 渠道商价
     */
    @ApiModelProperty(value = "渠道商价格")
    private BigDecimal dealerPrice;


    /**
     * 充值提示信息
     */
    @ApiModelProperty(value = "充值提示信息")
    private String tips;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * 渠道商主键
     */
    @ApiModelProperty(value = "渠道商主键id")
    private String dealerId;

    /**
     * 状态 0 下架 1上架  默认0
     */
    @ApiModelProperty(value = "状态：0下架，1上架，默认0")
    private Integer status = 0;


}
