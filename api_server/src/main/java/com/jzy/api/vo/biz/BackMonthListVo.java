package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BackMonthListVo extends GenericVo {
    /**
     * 时间间隔
     */
    private String timeInterval;
    /**
     * 总面值
     */
    private BigDecimal priceTotal;
    /**
     * 实付总金额
     */
    private BigDecimal tradeFeeTotal;
    /**
     * 渠道商价格总金额
     */
    private BigDecimal dealerPriceTotal;
    /**
     * 商户编号
     */
    private String dealerNum;
    /**
     * 商户名称
     */
    private String dealerName;
//    /**
//     * 商户李瑞总金额
//     */
//    private BigDecimal merchantProfitTotal;

    /**
     * <b>功能描述：</b>商户利润计算<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public BigDecimal getMerchantProfitTotal() {
        return this.tradeFeeTotal.subtract(this.dealerPriceTotal);
    }
}
