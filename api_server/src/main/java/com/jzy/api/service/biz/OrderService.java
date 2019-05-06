package com.jzy.api.service.biz;

import com.jzy.api.model.biz.Order;
import com.jzy.framework.service.GenericService;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <b>功能：</b>订单业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface OrderService extends GenericService<Order> {

    /**
     * <b>功能描述：</b>根据流水查询订单号<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    Order queryOrderByOutTradeNo(String outTradeNo);

    /**
     * <b>功能描述：</b>新增或修改订单<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    String insertOrUpdateOrder(HttpServletRequest request, Order order, Integer payWayId);

    /**
     * <b>功能描述：</b>订单退款<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int tradeRefund(Order order);

    /**
     * <b>功能描述：</b>查询订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    int queryOrderStatus(String orderId);

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<Order> queryOrderList();

    /**
     * <b>功能描述：</b>根据订单id删除订单<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    int delete(String id);

    /**
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    Order queryOrderById(String id);

    /**
     * <b>功能描述：</b>根据订单id查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    String queryCardPwdById(String id);

    /**
     * <b>功能描述：</b>根据订单id关闭订单<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    int updateOrderClose(String id);

    /**
     * <b>功能描述：</b>更新订单充值状态<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id     主键id
     * @param status 0等待支付,1充值中,2充值成功,3充值失败,4订单关闭
     */
    int updateStatus(String id, Integer status);

    /**
     * <b>功能描述：</b>更新订单交易状态(异步退款通知的时候更改交易状态)<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id          主键id
     * @param tradeStatus 订单交易状态 {@link OrderMapper.TradeStatusConst}
     */
    int updateTradeStatus(String id, String tradeStatus);

    /**
     * <b>功能描述：</b>这里写功能描述<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param status      充值状态 0等待支付,1充值中,2充值成功,3充值失败,4订单关闭
     * @param tradeStatus 交易状态 {@link OrderMapper.TradeStatusConst}
     * @param supStatus   sup状态
     * @param id          主键id
     */
    int updateStatusTradeStatusSupStatus(String id, Integer status, String tradeStatus, Integer supStatus);

    /**
     * <b>功能描述：</b>更新订单充值状态和支付方式(主要用户唤醒支付时候,更新支付方式,流水号,状态)<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id          主键id
     * @param status      充值状态
     * @param tradeMethod 支付方式
     * @param outTradeNo  订单流水号
     */
    int updateStatusTradeMethod(String id, Integer status, Integer tradeMethod, String outTradeNo);

    /**
     * <b>功能描述：</b>更新sup状态<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     * @param supStatus sup状态，0未提交1已提交2成功3失败
     */
    int updateSupStatus(@Param("id") String id, @Param("supStatus") Integer supStatus);

}
