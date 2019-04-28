package com.jzy.api.service.biz;

import com.jzy.api.model.biz.TradeRecord;
import com.jzy.framework.service.GenericService;

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
    boolean updateBgRespByIdStatus(String markId, String status, String bgRespData, String id, String oldStatus);

    /**
     * 更新交易记录
     * <p>
     * 1:(微信)退款通知
     *
     * @param markid     第三方支付订单号
     * @param status     [refushed、failed、passed、noresponse]
     * @param bgRespData 异步通知数据
     * @param operator   交易唯一标识trade_record_operator
     * @return
     */
    boolean updateBgRespByOperatorStatus(String markid, String status, String bgRespData, String operator, String oldStatus);

    /**
     * <b>功能描述：</b>更新交易记录<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * 1:(支付宝)支付完成回调
     * @param markid
     * @param status
     * @param respData
     * @param operator
     * @param oldStatus
     */
    boolean updateRespByOperatorStatus(String markid, String status, String respData, String operator, String oldStatus);

    /**
     * <b>功能描述：</b>查询<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param operator 操作订单号,操作人
     * @param type 交易类型(支付:pay、退款:refund)
     * @param status 交易状态
     */
    TradeRecord queryOperator(String operator, String type , String status);
}
