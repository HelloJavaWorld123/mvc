package com.jzy.api.service.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.biz.BackOrderCnd;
import com.jzy.api.cnd.biz.MonthOrderCnd;
import com.jzy.api.cnd.biz.RunMonthOrderCnd;
import com.jzy.api.dao.biz.OrderMapper;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.SupRecord;
import com.jzy.api.model.biz.TradeRecord;
import com.jzy.api.service.biz.*;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.DateUtils;
import com.jzy.api.vo.biz.FrontOrderVo;
import com.jzy.framework.bean.vo.PageVo;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.jzy.api.model.biz.Order.TradeStatusConst.REFUND_SICCESS;

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

    @Resource
    private CardPwdService cardPwdService;

    @Resource
    private TradeRecordService tradeRecordService;

    @Resource
    private SupService supService;

    /**
     * 订单超时时间
     */
    private static final int TIMEOUT = 15 * 60 * 1000;

    /**
     * <b>功能描述：</b>新增或修改订单<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public String insertOrUpdateOrder(HttpServletRequest request, Order order, Integer payWayId) {
        String orderId = order.getOrderId();
        if (!StringUtils.isEmpty(orderId)) {
            log.debug("current order is not null, order id is " + orderId);
            order = orderMapper.queryOrderById(order.getOrderId());
            if (order == null) {
                throw new BusException("订单不存在！");
            }
            if (order.getStatus() != 0) {
                throw new BusException("订单已支付！");
            }
            // 校验订单是否已超过15分钟失效时间
            if (new Date().getTime() - order.getCreateTime().getTime() > TIMEOUT) {
                throw new BusException("订单已超时，请重新选择商品进行支付！");
            }
        }
        // 设置支付方式，防止重新支付，更换支付方式
        order.setTradeMethod(payWayId);
        order.setUserId(getUserId());
        // 是否临时订单
        boolean isTempOrder = false;
        if (StringUtils.isEmpty(orderId)) {
            isTempOrder = true;
            order.setOrderId(CommUtils.uniqueOrderStr());
            order.setCode(DateUtils.date2TimeStr(new Date()).concat(CommUtils.authCode()));
            order.setDealerId(getFrontDealerId());
        }
        order.setOutTradeNo(order.getOrderId().concat(CommUtils.getStringRandom(7)));
        // 获取具体的支付方式
        PayService payService = paywayProvider.getPayService(payWayId);
        // 支付
        ApiResult apiResult;
        try {
            apiResult = payService.pay(request, order);
        } catch (Exception e) {
            throw new PayException("支付异常");
        }
        // 是否临时订单
        if (isTempOrder) {
            // 保存或更新Order订单数据
            insert(order);
        } else {
            // 重新支付，更新订单的支付方式
            updateStatusTradeMethod(order.getOrderId(), order.getStatus(), order.getTradeMethod(), order.getOutTradeNo());
        }
        return apiResult.getData().toString();
    }

    /**
     * <b>功能描述：</b>订单退款<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return int 0：退单成功；1：退单失败
     */
    @Override
    public int tradeRefund(Order order) {
        PayService payService = paywayProvider.getPayService(order.getTradeMethod());
        boolean isSuccess = payService.orderBack(order);
        if (isSuccess) {
            return update(order);
        }
        return 0;
    }

    /**
     * <b>功能描述：</b>查询订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    @Override
    public int queryOrderStatusForParty(String orderId) {
        Order order = orderMapper.queryOrderById(orderId);
        if (order == null) {
            throw new BusException("订单不存在");
        }
        PayService payService = paywayProvider.getPayService(order.getTradeMethod());
        // 查询支付状态
        int status = payService.queryOrderStatus(order);
        // 更新支付状态
        orderMapper.updateStatus(order.getOrderId(), status);
        return 0;
    }

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo<FrontOrderVo> queryFrontOrderList(Integer curPage, Integer limit, Integer status) {
        Page<Order> page = PageHelper.startPage(curPage, limit);
        List<Order> orderList = orderMapper.queryFrontOrderList(status, getUserId());
        PageVo<FrontOrderVo> pageVo = new PageVo<>();
        pageVo.setPage(page.getPageNum());
        pageVo.setLimit(page.getPageSize());
        pageVo.setTotalCount(page.getTotal());
        pageVo.setRows(getFrontOrderVo(orderList));
        return pageVo;
    }

    /**
     * <b>功能描述：</b>返回参数赋值<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private List<FrontOrderVo> getFrontOrderVo(List<Order> orderList) {
        if (orderList == null || orderList.isEmpty()) {
            return Collections.emptyList();
        }
        List<FrontOrderVo> frontOrderVoList = new ArrayList<>();
        for (Order order : orderList) {
            FrontOrderVo frontOrderVo = new FrontOrderVo();
            frontOrderVo.setOrderId(order.getOrderId());
            frontOrderVo.setCode(order.getCode());
            frontOrderVo.setPrice(order.getPrice());
            frontOrderVo.setTotalFee(order.getTotalFee());
            frontOrderVo.setTradeFee(order.getTradeFee());
            frontOrderVo.setStatus(order.getStatus());
            frontOrderVo.setPriceTypeName(order.getPriceTypeName());
            frontOrderVo.setCreateTime(order.getCreateTime());
            frontOrderVo.setAppId(order.getAppId() + "");
            frontOrderVo.setAppName(order.getAppName());
            frontOrderVo.setAppIcon(order.getAppIcon());
            frontOrderVoList.add(frontOrderVo);
        }
        return frontOrderVoList;
    }

    /**
     * <b>功能描述：</b>根据订单id删除订单<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    @Override
    public int delete(String id) {
        return orderMapper.updateOrderDelFlag(id);
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
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    @Override
    public Order queryOrderDetail(String id) {
        Order order = orderMapper.queryOrderDetail(id);
        // 卡密需要查询卡密信息
        if (order.getRechargeMode() == 1) {
            // 根据订单id查询卡号
            String cardNo = cardPwdService.queryCardNoByOrderId(id);
            if (!StringUtils.isEmpty(cardNo)) {
                order.setCardNo(cardNo);
            }
        }
        return order;
    }

    /**
     * <b>功能描述：</b>根据订单id查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    @Override
    public String queryCardPwdByOrderId(String orderId) {
        return orderMapper.queryCardPwdByOrderId(orderId);
    }

    /**
     * <b>功能描述：</b>更新充值状态<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int updateStatus(String id, Integer status) {
        return orderMapper.updateStatus(id, status);
    }

    /**
     * <b>功能描述：</b>更新交易状态<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id          订单id
     * @param tradeStatus 交易状态
     */
    @Override
    public int updateTradeStatus(String id, String tradeStatus) {
        return orderMapper.updateTradeStatus(id, tradeStatus);
    }

    /**
     * <b>功能描述：</b>更新状态<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id          订单id
     * @param status      订单状态 0：待支付；1：充值中；2：充值成功；3：充值失败；4：充值关闭
     * @param tradeStatus 支付状态
     * @param supStatus   sup状态，0：未提交；1：已提交；2：成功；3：失败
     */
    @Override
    public int updateStatusTradeStatusSupStatus(String id, Integer status, String tradeStatus, Integer supStatus) {
        return orderMapper.updateStatusTradeStatusSupStatus(id, status, tradeStatus, supStatus);
    }

    /**
     * <b>功能描述：</b>重新支付，更新订单的支付方式<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id          订单id
     * @param status      订单状态
     * @param tradeMethod 支付方式
     * @param outTradeNo  流水号
     */
    @Override
    public int updateStatusTradeMethod(String id, Integer status, Integer tradeMethod, String outTradeNo) {
        return orderMapper.updateStatusTradeMethod(id, status, tradeMethod, outTradeNo);
    }

    /**
     * <b>功能描述：</b>更新sup状态<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id        订单id
     * @param supStatus sup状态，0未提交1已提交2成功3失败
     */
    @Override
    public int updateSupStatus(String id, Integer supStatus) {
        return orderMapper.updateSupStatus(id, supStatus, new Date());
    }

    /**
     * <b>功能描述：</b>查询订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    @Override
    public Order queryOrderStatus(String orderId) {
        return orderMapper.queryOrderStatus(orderId);
    }

    /**
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190510&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public Order queryBackOrderById(String id) {
        Order order = orderMapper.queryBackOrderById(id);
        // 当订单为退款状态时，查询退款单号
        if (REFUND_SICCESS.equals(order.getTradeStatus())) {
            TradeRecord tradeRecord = tradeRecordService.queryRefundCodeByOutTradeNo(id);
            if (tradeRecord != null) {
                order.setRefundCode(tradeRecord.getTradeRecordId());
                order.setRefundOutTradeNo(tradeRecord.getMarkId());
            }
        }
        // 查询sup的购买金额或sup返回备注
        SupRecord supRecord = supService.queryPurchaserPriceAndRemarkByOrderId(id);
        if (supRecord != null) {
            order.setPurchaserPrice(supRecord.getPurchaserPrice());
            order.setSupRemark(supRecord.getRemark());
        }
        return order;
    }

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo<Order> queryBackOrderList(BackOrderCnd backOrderCnd) {
        Page page = PageHelper.startPage(backOrderCnd.getPage(), backOrderCnd.getLimit());
        // 订单列表查询
        List<Order> orderList = orderMapper.queryBackOrderList(backOrderCnd.getStartDate(), backOrderCnd.getEndDate(),
                backOrderCnd.getSupStatus(), backOrderCnd.getStatus(), backOrderCnd.getKey());
        if (orderList == null || orderList.isEmpty()) {
            return new PageVo<>();
        }
        return new PageVo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), orderList);
    }

    /**
     * <b>功能描述：</b>订单列表已完成订单统计<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public Order queryBackOrderCount(BackOrderCnd backOrderCnd) {
        Page page = PageHelper.startPage(backOrderCnd.getPage(), backOrderCnd.getLimit(), false);
        return orderMapper.queryBackOrderCount(backOrderCnd.getStartDate(), backOrderCnd.getEndDate(),
                backOrderCnd.getSupStatus(), backOrderCnd.getStatus(), backOrderCnd.getKey());
    }

    /**
     * <b>功能描述：</b>月订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo<Order> queryMonthOrderList(MonthOrderCnd monthOrderCnd) {
        Page page = PageHelper.startPage(monthOrderCnd.getPage(), monthOrderCnd.getLimit());
        List<Order> orderList = orderMapper.queryMonthOrderList(monthOrderCnd.getStartDate(),
                monthOrderCnd.getEndDate(), monthOrderCnd.getKey());
        if (orderList == null || orderList.isEmpty()) {
            return new PageVo<>();
        }
        return new PageVo<>(page.getPageNum(), page.getPageSize(), page.getTotal(), orderList);
    }

    /**
     * <b>功能描述：</b>归档月账单数据<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void runMonthOrderList(RunMonthOrderCnd runMonthOrderCnd) {

    }

    @Override
    protected GenericMapper getGenericMapper() {
        return orderMapper;
    }
}
