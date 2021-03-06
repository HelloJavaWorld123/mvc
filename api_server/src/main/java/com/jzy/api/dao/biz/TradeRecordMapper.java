package com.jzy.api.dao.biz;

import com.jzy.api.model.biz.TradeRecord;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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
public interface TradeRecordMapper extends GenericMapper<TradeRecord> {

    /**
     * <b>功能描述：</b>更新交易记录_(支付宝)支付完成回调<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param markId
     * @param status
     * @param respData
     * @param operator
     * @param oldStatus
     */
    int updateAliPayCallbackStatus(@Param("markId") String markId, @Param("status") Integer status,
                                        @Param("respData") String respData, @Param("operator") String operator,
                                        @Param("oldStatus") Integer oldStatus);

    /**
     * <b>功能描述：</b>更新交易记录<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param markId     第三方支付订单号
     * @param status     [refushed、failed、passed、noresponse]
     * @param bgRespData 异步通知数据
     * @param id         交易唯一标识trade_record_id
     */
    int updateWxCallbackStatus(@Param("markId") String markId, @Param("status") Integer status,
                               @Param("bgRespData") String bgRespData, @Param("id") String id,
                               @Param("oldStatus") Integer oldStatus);

    /**
     * <b>功能描述：</b>更新交易记录_(微信)退款通知<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param markId     第三方支付订单号
     * @param status     [refushed、failed、passed、noresponse]
     * @param bgRespData 异步通知数据
     * @param operator   交易唯一标识trade_record_operator
     */
    int updateBgRespByOperatorStatus(@Param("markId") String markId, @Param("status") Integer status,
                                     @Param("bgRespData") String bgRespData, @Param("operator") String operator,
                                     @Param("oldStatus") Integer oldStatus);



    /**
     * <b>功能描述：</b>查询<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param operator 操作订单号,操作人
     * @param type 交易类型(支付:pay、退款:refund)
     * @param status 交易状态
     */
    TradeRecord queryOperator(@Param("operator") String operator,
                                    @Param("type") Integer type ,
                                    @Param("status") Integer status);

    /**
     * <b>功能描述：</b>当订单为退款状态时，查询退款单号<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    TradeRecord queryRefundCodeByOutTradeNo(@Param("id") String id);

    /** 根据 outTradeNo查询支付流水记录的id
     * @Description
     * @Author lchl
     * @Date 2019/5/28 6:27 PM
     * @param outTradeNo
     * @return java.lang.String
     */
    String queryIdByOutTradeNo(@Param("outTradeNo") String outTradeNo);
    /**
     * @Description
     * @Author lchl
     * @Date 2019/5/29 2:01 PM
     * @param paramsMap
     * @return java.lang.String
     */
    String queryIdByParams(Map<String, Object> paramsMap);

    int updateXimeiPayCallbackStatus(@Param("markId") String markId, @Param("status") Integer status,
                                      @Param("respData") String respData, @Param("operator") String operator,
                                      @Param("oldStatus") Integer oldStatus);

    TradeRecord queryInfoByParams(Map<String, Object> paramsMap);

    int updateXimeiRefundCallbackStatus(@Param("markId") String markId, @Param("status") Integer status,
                                        @Param("bgRespData") String bgRespData, @Param("operator") String operator);

    List<TradeRecord> getTradeRecord(@Param("id") String id);
}
