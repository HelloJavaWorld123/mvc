package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>订单详情返回参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class FrontOrderVo extends GenericVo {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 面值
     */
    private BigDecimal price;
    /**
     * 应付金额
     */
    private BigDecimal totalFee;
    /**
     * 实付金额（用户实际支付的金额）
     */
    private BigDecimal tradeFee;
    /**
     * 交易状态 status 0：等待支付,1：充值中,2：充值成功,3：充值失败,4：订单关闭
     */
    private Integer status;
    /**
     * 充值类型名称
     */
    private String priceTypeName;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 商品id
     */
    private String appId;
    /**
     * 商品名称
     */
    private String appName;
    /**
     * 商品图标
     */
    private String appIcon;

}
