package com.jzy.api.model.dealer;

import com.jzy.framework.bean.model.GenericModel;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>渠道商商品定价<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAppPriceInfo extends GenericModel {
    /**
     * 商品主键
     */
    private String aiId;

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
    private BigDecimal payPrice=BigDecimal.ZERO;

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
     * 是否自定义金额
     */
    private Integer isCustom;

    public Integer getIsCustom() {
        if (this.isCustom == null) {
            return 0;
        }
        return isCustom;
    }

    /**
     * <b>功能描述：</b>获取实际的支付金额<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public BigDecimal getActualPayAmount(BigDecimal discount) {
        if (discount.compareTo(BigDecimal.ZERO) == 0) {
            return this.price.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return this.price.multiply(this.discount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * <b>功能描述：</b>获取实际的支付金额<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public BigDecimal getCustomPayAmount() {
        if (this.discount.compareTo(BigDecimal.ZERO) == 0) {
            return this.price.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return this.price.multiply(this.discount).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
