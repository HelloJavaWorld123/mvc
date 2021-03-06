package com.jzy.api.model.biz;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>功能：</b>订单<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class Order extends GenericModel {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 第三方支付账号
     */
    private String markId;
    /**
     * 订单编号:时间戳+6位随机数
     */
    private String code;
    /**
     * 支付/提交SUP订单 - 商户订单号
     */
    private String outTradeNo;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 充值模式
     * 0直充 1 卡密
     */
    private Integer rechargeMode;

    /**
     * sup商品提交分组编号
     */
    private String supNo;
    /**
     * sup商品提交金额
     */
    private BigDecimal supPrice;
    /**
     * sup状态，0未提交;1已提交;2成功;3失败
     */
    private Integer supStatus = 0;
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
     * 订单类型:1服务,2游戏,3商品
     */
    private Integer type;
    /**
     * 订单应付总金额
     */
    private BigDecimal totalFee;
    /**
     * 充值类型名称
     */
    private String priceTypeName;
    /**
     * 充值类型单位
     */
    private String priceTypeUnit;
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
     * 支付交易号
     */
    private String tradeCode = "";
    /**
     * 实付总金额
     */
    private BigDecimal tradeFee = BigDecimal.ZERO;
    /**
     * 渠道商价格
     */
    private BigDecimal dealerPrice = BigDecimal.ZERO;
    /**
     * 支付交易状态
     */
    private String tradeStatus;
    /**
     * 开票状态
     */
    private Integer invoiceStatus;
    /**
     * 备注
     */
    private String remark = "";
    /**
     * 商户id
     */
    private String dealerId;


    //--------------------序列-----------------------------
    private List<CardPwd> cardPwdList;
    /**
     * 商品id
     */
    private Long appId;
    /**
     * 商品code
     */
    private String appCode;
    /**
     * 充值类型id
     */
    private Long aptId;
    /**
     * 商品名称
     */
    private String appName;
    /**
     * 商品图片
     */
    private String appIcon;
    /**
     * 卡号
     */
    private String cardNo;
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
     * 支付时间
     */
    private Date payTime;
    /**
     * 完成时间/到账时间
     */
    private Date finishTime;
    /**
     * 总面值
     */
    private BigDecimal priceTotal = BigDecimal.ZERO;
    /**
     * 应付总金额
     */
    private BigDecimal totalFeeTotal = BigDecimal.ZERO;
    /**
     * 实付总金额
     */
    private BigDecimal tradeFeeTotal = BigDecimal.ZERO;
    /**
     * 渠道商价格总金额
     */
    private BigDecimal dealerPriceTotal = BigDecimal.ZERO;
    /**
     * 时间间隔
     */
    private String timeInterval;

    /**
     * 是否微信授权
     * 1：是
     */
    private Integer isWxAuth = 0;
    /**
     * 订单交易状态
     */
    public class TradeStatusConst {

        /** 待支付 */
        public static final String WAIT_PAY = "wait_pay";

        /** 已支付可以退款 */
        public static final String PAY_SUCCESS = "pay_success";
        /** 支付失败 */
        public static final String PAY_FAI = "pay_fai";

        /** 待退款 */
        public static final String WAIT_REFUND = "wait_refund";

        /** 已退款 */
        public static final String REFUND_SICCESS = "refund_success";

        /** 退款驳回 */
        public static final String REFUND_REJECT = "refund_reject";

        /** 完成不可以退款 */
        public static final String FINISHED = "finished";

    }
}
