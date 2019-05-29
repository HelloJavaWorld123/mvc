package com.jzy.api.service.biz;

import com.jzy.api.model.biz.TradeRecord;
import com.jzy.framework.service.GenericService;

import java.util.Map;

/**
 * <b>功能：</b>交易记录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface TradeRecordService extends GenericService<TradeRecord> {

    /**
     * <b>功能描述：</b>更新交易记录_支付宝支付完成回调<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param markId 支付宝返回的流水号
     * @param status 交易状态；3：失败；4：成功
     * @param respData 支付宝返回的结果
     * @param operator API的流水号，对应于订单Order表中的id
     * @param oldStatus 支付原始状态
     */
    int updateAliPayCallbackStatus(String markId, Integer status, String respData, String operator, Integer oldStatus);

    /**
     * <b>功能描述：</b>更新交易记录<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param markId 第三方支付订单号
     * @param status [refushed、failed、passed、noresponse]
     * @param bgRespData 异步通知数据
     * @param id 交易唯一标识trade_record_id
     * @param oldStatus
     */
    int updateWxCallbackStatus(String markId, Integer status, String bgRespData, String id, Integer oldStatus);

    /**
     * 更新交易记录
     * <p>
     * 1:(微信)退款通知
     *
     * @param markid     第三方支付订单号
     * @param status     状态(0 sended、1 waited、2 wrong、3 failed、4 passed、5 refushed、6 noresponse)
     * @param bgRespData 异步通知数据
     * @param operator   交易唯一标识trade_record_operator
     * @return
     */
    int updateBgRespByOperatorStatus(String markId, Integer status, String bgRespData, String operator, Integer oldStatus);

    /**
     * <b>功能描述：</b>查询<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param operator 操作订单号,操作人
     * @param type 交易类型(支付:pay、退款:refund)
     * @param status 交易状态
     */
    TradeRecord queryOperator(String operator, Integer type , Integer status);

    /**
     * <b>功能描述：</b>当订单为退款状态时，查询退款单号<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    TradeRecord queryRefundCodeByOutTradeNo(String outTradeNo);
    /** 根据 outTradeNo查询支付流水记录的id
     * @Description
     * @Author lchl
     * @Date 2019/5/28 6:27 PM
     * @param outTradeNo
     * @return java.lang.String
     */
    String queryIdByOutTradeNo(String outTradeNo);

    String queryIdByParams(Map<String, Object> paramsMap);
}
