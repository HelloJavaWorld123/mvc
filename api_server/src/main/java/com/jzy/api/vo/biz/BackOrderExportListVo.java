package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>订单列表导出参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class BackOrderExportListVo extends GenericVo{
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
    private BigDecimal tradeFee = BigDecimal.ZERO;
    /**
     * 渠道商价格
     */
    private BigDecimal dealerPrice = BigDecimal.ZERO;
    /**
     * 支付方式
     */
    private String payWayName;
    /**
     * 订单状态 0待支付,1充值中(已支付),2充值成功,3充值失败,4充值关闭
     */
    private String status;
    /**
     * sup状态，0未提交;1已提交;2成功;3失败
     */
    private String supStatus;
    /**
     * 充值时间
     */
    private Date payTime;
    private Date createTime;
    /**
     * 渠道商利润
     */
    private BigDecimal merchantProfit = BigDecimal.ZERO;

    public BigDecimal getMerchantProfit() {
        return this.tradeFee.subtract(this.dealerPrice);
    }

    /**
     * <b>功能描述：</b>支付方式<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public void setPayWayName(int payWayId) {
        if (payWayId == 0) {
            this.payWayName = "微信";
            return;
        }
        if (payWayId == 1) {
            this.payWayName = "支付宝";
            return;
        }
        if (payWayId == 2) {
            this.payWayName = "西煤支付";
            return;
        }
    }

    /**
     * <b>功能描述：</b>订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public void setStatus(int status) {
        if (status == 0) {
            this.status = "待支付";
            return;
        }
        if (status == 1) {
            this.status = "充值中";
            return;
        }
        if (status == 2) {
            this.status = "充值成功";
            return;
        }
        if (status == 3) {
            this.status = "退款中";
            return;
        }
        if (status == 4) {
            this.status = "充值关闭";
            return;
        }
        this.status = "退款成功";
    }

    /**
     * <b>功能描述：</b>sup订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public void setSupStatus(int supStatus) {
        if (supStatus == 0) {
            this.supStatus = "未提交";
            return;
        }
        if (supStatus == 1) {
            this.supStatus = "处理中";
            return;
        }
        if (supStatus == 2) {
            this.supStatus = "成功";
            return;
        }
        this.supStatus = "失败";
    }
}
