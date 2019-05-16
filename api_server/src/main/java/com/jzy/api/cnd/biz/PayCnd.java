package com.jzy.api.cnd.biz;

import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.GenericCnd;
import com.jzy.framework.exception.BusException;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
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
    @Range(max = 1)
    @NotNull
    private Integer tradeMethod;
    /**
     * 充值模式
     * 0直充 1 卡密
     */
    @Range(max = 1)
    @NotNull
    private Integer rechargeMode;
    /**
     * 订单应付总金额
     */
    @NotNull
    private BigDecimal totalFee;
    /**
     * 实付总金额
     */
    @NotNull
    private BigDecimal tradeFee;
    /**
     * 渠道商价格
     */
    @NotNull
    private BigDecimal dealerPrice;
    /**
     * 折扣
     */
    private BigDecimal discount = BigDecimal.ZERO;
    /**
     * sup商品提交金额
     */
    @NotNull
    private BigDecimal supPrice;
    /**
     * sup商品编号
     */
    @NotBlank
    private String supNo;
    /**
     * 购买数量
     */
    @NotNull
    private Integer number;
    /**
     * 订单类型:1服务,2游戏,3商品
     */
    @Range(min = 1, max = 3)
    @NotNull
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
    @NotNull
    private Long appId;
    /**
     * 商品名称
     */
    @NotBlank
    private String appName;

    /**
     * <b>功能描述：</b>校验支付金额<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public void validateTradeFee(BigDecimal actualPayAmount) {
        int isEqual = this.tradeFee.compareTo(actualPayAmount);
        if (isEqual != 0) {
            throw new BusException(ResultEnum.TRADE_FEE_CALC_ERROR);
        }
    }
}
