package com.jzy.api.model.biz;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

@Data
public class TradeRecord extends GenericModel {
    private String tradeRecordId;
    /**
     * 交易记录标识(微信、支付宝交易流水号)
     */
    private String markId;
    /**
     * 操作订单号、操作人
     */
    private String operator;
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
     * 状态
     * (0 sended、1 waited、2 wrong、3 failed、4 passed、5 refushed、6 noresponse)
     */
    private Integer status = 0;
    /**
     * 交易类型
     * (0 支付:pay、1 退款:refund)
     */
    private Integer type;
    /**
     * 返回时间
     */
    private Date respTime;
    /**
     * 返回数据
     */
    private String respData;
    /**
     * 渠道(0 微信、1 支付宝)
     * 服务标识(微信、支付宝、乐信通)
     */
    private Integer trusteeship;

    private Date bgRespTime;

    private String bgRespData;

    public TradeRecord() {
    }

    public TradeRecord(String id, String operator, Date reqTime, String reqUrl, String reqData, Integer status, Integer type, Date respTime, String respData, Integer trusteeship) {
        this.tradeRecordId = id;
        this.operator = operator;
        this.reqTime = reqTime;
        this.reqUrl = reqUrl;
        this.reqData = reqData;
        this.status = status;
        this.type = type;
        this.respTime = respTime;
        this.respData = respData;
        this.trusteeship = trusteeship;
    }

}
