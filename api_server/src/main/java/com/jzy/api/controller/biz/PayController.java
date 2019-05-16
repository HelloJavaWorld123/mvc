package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.CodeCnd;
import com.jzy.api.cnd.biz.PayCnd;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.arch.DealerAppInfoService;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.vo.biz.StatusVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(path = "/pay")
public class PayController extends GenericController {

    @Resource
    private OrderService orderService;

    @Resource
    private DealerAppInfoService dealerAppInfoService;

    /**
     * <b>功能描述：</b>请求支付下单,初始化/更新订单,调起微信/支付宝<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("/pay")
    public ApiResult pay(HttpServletRequest request, @RequestBody PayCnd payCnd) {
        log.debug("支付请求参数为：" + payCnd.toString());
        // 数据一致性校验
        validate(payCnd);
        ApiResult<String> apiResult = new ApiResult<>();
        Order order = getOrder(payCnd);
        String linkUrl = orderService.insertOrUpdateOrder(request, order, payCnd.getTradeMethod());
        apiResult.setData(linkUrl);
        log.debug("支付返回url地址为: " + linkUrl);
        return apiResult.success();
    }

    /**
     * <b>功能描述：</b>数据一致性校验<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void validate(PayCnd payCnd) {
        // 根据商品id获取商品信息
        Integer status = dealerAppInfoService.queryAppStatus(payCnd.getAppId());
        if (status == null) {
            throw new BusException(ResultEnum.APP_NOT_EXIST);
        }
        if (status != 0) {
            throw new BusException(ResultEnum.APP_OFF_SHELVES);
        }
        // 校验商品的价格，支付金额信息
        
    }

    /**
     * <b>功能描述：</b>主动去查询订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("/updateOrderStatusByActiveQuery")
    public ApiResult updateOrderStatusByActiveQuery(@RequestBody CodeCnd codeCnd) {
        ApiResult<StatusVo> apiResult = new ApiResult<>();
        int status = orderService.updateOrderStatusByActiveQuery(codeCnd.getOrderId());
        StatusVo statusVo = new StatusVo();
        statusVo.setStatus(status);
        return apiResult.success(statusVo);
    }

    /**
     * <b>功能描述：</b>构建订单参数<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Order getOrder(PayCnd payCnd) {
        Order order = new Order();
        order.setOrderId(payCnd.getOrderId());
        if (order.getOrderId() != null) {
            order.setTradeMethod(payCnd.getTradeMethod());
        }
        order.setTotalFee(payCnd.getTotalFee());
        order.setPrice(payCnd.getTotalFee());
        order.setTradeFee(payCnd.getTradeFee());
        order.setDiscount(payCnd.getDiscount());
        order.setSupPrice(payCnd.getSupPrice());
        order.setSupNo(payCnd.getSupNo());
        order.setNumber(payCnd.getNumber());
        order.setType(payCnd.getType());
        order.setPriceTypeName(payCnd.getPriceTypeName());
        order.setPriceTypeUnit(payCnd.getPriceTypeUnit());
        order.setAcctType(payCnd.getAcctType());
        order.setAccount(payCnd.getAccount());
        order.setGameAccount(payCnd.getGameAccount());
        order.setGameArea(payCnd.getGameArea());
        order.setGameServ(payCnd.getGameServ());
        order.setAppId(payCnd.getAppId());
        order.setAppName(payCnd.getAppName());
        order.setRechargeMode(payCnd.getRechargeMode());
        // 交易状态为待支付
        order.setTradeStatus(Order.TradeStatusConst.WAIT_PAY);
        order.setDealerPrice(payCnd.getDealerPrice());
        return order;
    }
    
}
