package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

/**
 * <b>功能：</b>订单详情中的商品信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190420&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class OrderAppInfoVo extends GenericVo {
    /**
     * 商户编号
     */
    private String dealerNo;
    /**
     * 商户名称
     */
    private String dealerName;
    /**
     * 商品编号
     */
    private String appNo;
    /**
     * 商品名称
     */
    private String appName;
    /**
     * 面值
     */
    private Double faceValue;
    /**
     * 金额
     */
    private Double amount;
    /**
     * 应付金额
     */
    private Double shouldPayAmount;
    /**
     * 账号类型
     */
    private String accountType;
    /**
     * 游戏大区
     */
    private String gameArea;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 折扣
     */
    private Double discount;
    /**
     * 充值类型
     */
    private String rechargeType;
    /**
     * 充值账号
     */
    private String rechargeAccount;
    /**
     * 游戏区服
     */
    private String gameServ;

}