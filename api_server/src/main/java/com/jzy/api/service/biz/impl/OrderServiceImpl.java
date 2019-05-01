package com.jzy.api.service.biz.impl;

import com.jzy.api.dao.biz.OrderMapper;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.PayService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.DateUtils;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.exception.PayException;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.impl.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
@Slf4j
@Service
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private PaywayProvider paywayProvider;

    /**
     * <b>功能描述：</b>根据流水查询订单号<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public Order queryOrderByOutTradeNo(String outTradeNo) {
        return null;
    }

    /**
     * <b>功能描述：</b>新增或修改订单<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public String insertOrUpdateOrder(HttpServletRequest request, Order order) {
        String orderId = order.getOrderId();
        if (!StringUtils.isEmpty(orderId)) {
            order = orderMapper.queryOrderById(order.getOrderId());
            if (order == null) {
                throw new BusException("订单不存在！");
            }
            if (order.getStatus() == 0) {
                throw new BusException("订单已支付！");
            }
        }
        // 是否临时订单
        boolean isTempOrder = false;
        // TODO: 2019/4/30 是否进行登录
        if (orderId == null) {
            isTempOrder = true;
            order.setOrderId(CommUtils.uniqueOrderStr());
            order.setCode(DateUtils.date2TimeStr(new Date()).concat(CommUtils.authCode()));
        }
        order.setOutTradeNo(order.getOrderId().concat(CommUtils.getStringRandom(7)));

        // 获取具体的支付方式
        PayService payService = paywayProvider.getPayService(order.getTradeMethod());
        // 支付
        ApiResult apiResult;
        try {
            apiResult = payService.pay(request, order);
        } catch (Exception e) {
            throw new PayException("支付异常");
        }
        // 三 保存或更新Order订单数据
        if (isTempOrder) {
            insert(order);
        } else {
            // 更新订单的支付方式，流水号，交易状态
            updateStatusTradeMethod(order.getOrderId(), order.getStatus(), order.getTradeMethod(), order.getOutTradeNo());
        }

        return apiResult.getData().toString();
    }

    /**
     * <b>功能描述：</b>订单退款<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int tradeRefund(Order order) {
        PayService payService = paywayProvider.getPayService(order.getTradeMethod());
        payService.orderBack(order);
        return update(order);
    }

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nb
     * sp;创建方法</li><br>
     */
    @Override
    public List<Order> queryOrderList() {
        return orderMapper.queryOrderList();
    }

    /**
     * <b>功能描述：</b>根据订单id删除订单<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    @Override
    public int delete(Long id) {
        return 0;
    }

    /**
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    @Override
    public Order queryOrderById(String id) {
        return orderMapper.queryOrderById(id);
    }

    /**
     * <b>功能描述：</b>根据订单id查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    @Override
    public String queryCardPwdById(Long id) {
        return orderMapper.queryCardPwdById(id);
    }

    /**
     * <b>功能描述：</b>根据订单id关闭订单<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    @Override
    public int updateOrderClose(Long id) {
        return orderMapper.updateOrderClose(id);
    }

    @Override
    public int updateStatus(String id, Integer status) {
        return orderMapper.updateStatus(id, status);
    }

    @Override
    public int updateTradeStatus(String id, String tradeStatus) {
        return orderMapper.updateTradeStatus(id, Integer.parseInt(tradeStatus));
    }

    @Override
    public int updateStatusTradeStatusSupStatus(String id, Integer status, String tradeStatus, Integer supStatus) {
        return orderMapper.updateStatusTradeStatusSupStatus(id, status, tradeStatus, supStatus);
    }

    @Override
    public int updateStatusTradeMethod(String id, Integer status, Integer tradeMethod, String outTradeNo) {
        return orderMapper.updateStatusTradeMethod(id, status, tradeMethod, outTradeNo);
    }

    @Override
    protected GenericMapper getGenericMapper() {
        return orderMapper;
    }
}
