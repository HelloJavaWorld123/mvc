package com.jzy.api.model.biz;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>SUP充值记录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SupRecord extends GenericModel {
    /**
     * 主键
     */
    private String supRecordId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 请求数据
     */
    private String reqData;
    /**
     * 最新提交时间
     */
    private Date reqTime;
    /**
     * 提交次数
     */
    private Integer reqAmount;
    /**
     * 同步返回数据
     */
    private String respData;
    /**
     * 异步返回数据
     */
    private String bgRespData;
    /**
     * 异步返回消息
     */
    private String bgRespMes;
    /**
     * 最新异步通知时间
     */
    private Date bgRespTime;
    /**
     * 异步通知次数
     */
    private Integer bgRespAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 采购商的价格
     */
    private BigDecimal purchaserPrice;

    public SupRecord() {
    }


    public SupRecord(String id, String orderId, String reqData, Date reqTime, Integer reqAmount, String respData, String remark) {
        this.orderId = id;
        this.orderId = orderId;
        this.reqData = reqData;
        this.reqTime = reqTime;
        this.reqAmount = reqAmount;
        this.respData = respData;
        this.remark = remark;
    }

}
