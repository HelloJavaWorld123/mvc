package com.jzy.api.po.dealer;

import lombok.Data;

/**
 * <b>功能：</b>渠道商列表查询返回<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerListPo {

    /**
     * 渠道商基础信息表主键
     */
    private String id;

    /**
     * 渠道商主键
     */
    private String dealerId;

    /**
     * 渠道商名称
     */
    private String dealerName;


    /**
     * 商户简称
     */
    private String dealerShortName;

    /**
     * 收款方式
     */
    private String dealerPaymentMethod;

    /**
     * 状态
     */
    private String state;

    /**
     * 商户编号
     */
    private String dealerNo;
}
