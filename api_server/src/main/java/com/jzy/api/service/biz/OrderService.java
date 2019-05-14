package com.jzy.api.service.biz;

import com.jzy.api.cnd.biz.BackOrderCnd;
import com.jzy.api.cnd.biz.MonthOrderCnd;
import com.jzy.api.cnd.biz.RunMonthOrderCnd;
import com.jzy.api.model.biz.Order;
import com.jzy.api.vo.biz.FrontOrderVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.service.GenericService;

import javax.servlet.http.HttpServletRequest;

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
    int queryOrderStatusForParty(String orderId);

    /**
     * <b>功能描述：</b>查询订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    Order queryOrderStatus(String orderId);

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<FrontOrderVo> queryFrontOrderList(Integer page, Integer limit, Integer status);

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
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    Order queryOrderDetail(String id);

    /**
     * <b>功能描述：</b>根据订单id查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    String queryCardPwdByOrderId(String id);

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
     * <b>功能描述：</b>更新状态<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     * @param status 订单状态 0：待支付；1：充值中；2：充值成功；3：充值失败；4：充值关闭
     * @param tradeStatus 支付状态
     * @param supStatus sup状态，0：未提交；1：已提交；2：成功；3：失败
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
    int updateSupStatus(String id, Integer supStatus);

    //===============================后端订单列表接口========================================

    /**
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190510&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    Order queryBackOrderById(String id);

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<Order> queryBackOrderList(BackOrderCnd backOrderCnd);

    /**
     * <b>功能描述：</b>订单列表统计<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    Order queryBackOrderCount(BackOrderCnd backOrderCnd);

    /**
     * <b>功能描述：</b>月订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<Order> queryMonthOrderList(MonthOrderCnd monthOrderCnd);

    /**
     * <b>功能描述：</b>归档月账单数据<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void runMonthOrderList(RunMonthOrderCnd runMonthOrderCnd);

}
