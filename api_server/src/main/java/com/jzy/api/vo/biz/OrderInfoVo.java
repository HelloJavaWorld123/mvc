package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>订单信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190420&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class OrderInfoVo extends GenericVo {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单状态
     */
    private String orderState;
    /**
     * 下单时间
     */
    private String orderTime;
    /**
     * 支付方式
     */
    private String payWayName;
    /**
     * 实付金额
     */
    private Double actualPayAmount;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 流水单号
     */
    private String flowCode;
    /**
     * 到账时间
     */
    private Date paymentTime;
    /**
     * SUP备注
     */
    private String supRemark;

    /**
     * 特殊备注
     */
    private String remark;
    /**
     * 退款单号——API系统生成的
     */
    private String returnOrderNo;
    /**
     * 退款返回单号——返回单号是支付渠道返回的
     */
    private String refundReturnNo;
    /**
     * 商户成本
     */
    private BigDecimal merchantCost;
    /**
     * 商户利润
     */
    private BigDecimal merchantProfit;

    public void setPayWayName(Integer payWayId) {
        if (payWayId == 0) {
            this.payWayName = "微信";
        }
        if (payWayId == 1) {
            this.payWayName = "支付宝";
        }
        this.payWayName = "银行卡";
    }

}