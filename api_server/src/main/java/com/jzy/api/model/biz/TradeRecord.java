package com.jzy.api.model.biz;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

@Data
public class TradeRecord extends GenericModel {
    /**
     * 交易记录标识(微信、支付宝交易流水号)
     */
    private String markId;
    /**
     * 操作订单号、操作人
     */
    private Long operatorId;
    /**
     * 请求时间
     */
    private Date reqTime;
    /**
     * 请求地址
     */
    private String reqUrl;
    /**
     * 请求数据
     */
    private String reqData;
    /**
     * 状态(0 sended、1 waited、2 wrong、3 failed、4 passed、5 refushed、6 noresponse)
     */
    private String status;
    /**
     * 交易类型(0 支付:pay、1 退款:refund)
     */
    private String type;
    /**
     * 返回时间
     */
    private Date respTime;
    /**
     * 返回数据
     */
    private String respData;
    /**
     * 渠道(0 微信、1 支付宝) 服务标识(微信、支付宝、乐信通)
     */
    private String trusteeship;
    private Date bgRespTime;
    private String bgRespData;

}
