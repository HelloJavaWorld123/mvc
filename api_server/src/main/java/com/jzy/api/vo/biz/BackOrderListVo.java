package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>订单列表返回参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class BackOrderListVo extends GenericVo {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单编号
     */
    private String code;
    /**
     * 第三方流水单号
     */
    private String outTradeNo;
    /**
     * 渠道商编号
     */
    private String dealerNum;
    /**
     * 渠道商名称
     */
    private String dealerName;
    /**
     * 充值帐号
     */
    private String account;
    /**
     * 商品名称
     */
    private String appName;
    /**
     * 面值
     */
    private BigDecimal price;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 支付金额
     */
    private BigDecimal totalFee;
    /**
     * 实付金额
     */
    private BigDecimal tradeFee;
    /**
     * 渠道商价格
     */
    private BigDecimal dealerPrice;
    /**
     * 支付方式
     */
    private Integer tradeMethod;
    /**
     * 订单状态 0待支付,1充值中(已支付),2充值成功,3充值失败,4充值关闭
     */
    private Integer status;
    /**
     * sup状态，0未提交;1已提交;2成功;3失败
     */
    private Integer supStatus;
    /**
     * 充值时间
     */
    private Date payTime;
//    /**
//     * 渠道商利润
//     */
//    private BigDecimal merchantProfit;

    public BigDecimal getMerchantProfit() {
        return this.tradeFee.subtract(this.dealerPrice);
    }
}
