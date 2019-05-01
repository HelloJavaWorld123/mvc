package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.GenericCnd;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <b>功能：</b>请求支付下单,初始化/更新订单,调起微信/支付宝<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@ToString
@Data
public class PayCnd extends GenericCnd {
    /**
     * 该订单id的传参是从待支付页面传递过来的
     */
    private String orderId;
    /**
     * 支付方式
     * 0微信,1支付宝
     */
    private Integer tradeMethod;
    /**
     * 充值模式
     * 0直充 1 卡密
     */
    private Integer rechargeMode;
    /**
     * 订单应付总金额
     */
    private BigDecimal totalFee;
    /**
     * 实付总金额
     */
    private BigDecimal tradeFee;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * sup商品提交金额
     */
    private BigDecimal supPrice;
    /**
     * sup商品编号
     */
    private String supNo;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 订单类型:1服务,2游戏,3商品
     */
    private Integer type;
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
     * 商品id
     */
    private Long appId;
    /**
     * 商品名称
     */
    private String appName;
}
