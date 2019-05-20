package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>功能：</b>订单详情<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class OrderDetailVo extends GenericVo {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 三方交易订单号
     */
    private String outTradeNo;
    /**
     * 订单总价
     */
    private BigDecimal totalFee;
    /**
     * 实付金额
     */
    private BigDecimal tradeFee;
    /**
     * 支付方式
     * 0：微信
     * 1：支付宝
     */
    private Integer tradeMethod;
    /**
     * 面值
     */
    private BigDecimal price;
    /**
     * 充值类型名称
     */
    private String priceTypeName;
    /**
     * 订单状态
     * 0待支付,1充值中,2充值成功,3充值失败,4充值关闭
     */
    private Integer status;
    /**
     * 充值账号
     */
    private String account;
    /**
     * 商品类型
     */
    private Integer type;
    /**
     * 充值类型
     */
    private String acctType;
    /**
     * 充值数量
     */
    private Integer number;
    /**
     * 充值单位
     */
    private String priceTypeUnit;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 订单完成时间
     */
    private Date finishTime;
    /**
     * 商品id
     */
    private String appId;
    /**
     * 商品名称
     */
    private String appName;
    /**
     * 商品图片
     */
    private String appIcon;
    /**
     * 1：卡密
     */
    private int rechargeMode = 0;

    /**
     * 支付交易状态
     */
    private String tradeStatus;

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
     * 卡号
     */
    private List<CardPwdVo> cardPwdList = new ArrayList<>();
}
