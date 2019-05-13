package com.jzy.api.cnd.arch;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>渠道商商品定价入参<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAppPriceInfoCnd {

    /**
     * 充值类型主键
     */
    private String aptId;


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
    private BigDecimal discount=BigDecimal.ZERO;


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


    /**
     * 是否允许用户自定义输入金额
     */
    private Integer isCustom=0;
}
