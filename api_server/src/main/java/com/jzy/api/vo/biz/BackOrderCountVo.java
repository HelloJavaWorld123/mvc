package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>这里写功能描述<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190514&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class BackOrderCountVo extends GenericVo {
    /**
     * 总面值
     */
    private BigDecimal priceTotal;
    /**
     * 应付总金额
     */
    private BigDecimal totalFeeTotal;
    /**
     * 实付总金额
     */
    private BigDecimal tradeFeeTotal;
    /**
     * 渠道商成本总金额
     */
    private BigDecimal dealerPriceTotal;
    /**
     * 商户利润总金额
     */
    private BigDecimal merchantProfitTotal;
}
