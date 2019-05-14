package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;

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
    private String supStatus;
}
