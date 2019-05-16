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
public class BackOrderDetailVo extends GenericVo {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单编号:时间戳+6位随机数
     */
    private String code;
    /**
     * 支付/提交SUP订单 - 商户订单号
     */
    private String outTradeNo;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 面值金额
     */
    private BigDecimal price;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 订单状态0待支付,1充值中(已支付),2充值成功,3充值失败,4充值关闭
     */
    private Integer status = 0;
    /**
     * 订单应付总金额
     */
    private BigDecimal totalFee;
    /**
     * 充值类型名称
     */
    private String priceTypeName;
    /**
     * 主账号类型名称
     */
    private String acctType;
    /**
     * 充值账号
     */
    private String account;
    /**
     * 游戏账号
     */
    private String gameAccount;
    /**
     * 游戏区
     */
    private String gameArea;
    /**
     * 游戏服
     */
    private String gameServ;
    /**
     * 支付方式:0微信,1支付宝,2银行卡
     */
    private Integer tradeMethod;
    /**
     * 实付总金额
     */
    private BigDecimal tradeFee = BigDecimal.ZERO;
    /**
     * 渠道商价格
     */
    private BigDecimal dealerPrice = BigDecimal.ZERO;
    /**
     * 是否删除
     */
    private Integer delFlag;
    /**
     * 商品id
     */
    private Long appId;
    /**
     * 商品名称
     */
    private String appName;
    /**
     * 购买金额
     */
    private BigDecimal purchaserPrice;
    /**
     * sup返回备注
     */
    private String supRemark;
    /**
     * 退单单号
     */
    private String refundCode;
    /**
     * 第三方返回流水单号
     */
    private String refundOutTradeNo;
    /**
     * 商户号
     */
    private String dealerNum;
    /**
     * 商户名称
     */
    private String dealerName;
    /**
     * 下单时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 完成/到账时间
     */
    private Date finishTime;

    /**
     * <b>功能描述：</b>商户利润计算<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public BigDecimal getMerchantProfit() {
        return this.tradeFee.subtract(this.dealerPrice);
    }
}