package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.CodeCnd;
import com.jzy.api.cnd.biz.PayCnd;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.po.app.AppStatus;
import com.jzy.api.service.arch.DealerAppInfoService;
import com.jzy.api.service.arch.DealerAppPriceInfoService;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.vo.biz.StatusVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/pay")
@Api(tags = "前端-下单流程")
public class PayController extends GenericController {

    @Resource
    private OrderService orderService;

    @Resource
    private DealerAppInfoService dealerAppInfoService;

    @Resource
    private DealerAppPriceInfoService dealerAppPriceInfoService;

    /**
     * <b>功能描述：</b>请求支付下单,初始化/更新订单,调起微信/支付宝<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("/pay")
    @ApiOperation(httpMethod="POST" ,value = "请求支付下单")
    public ApiResult pay(@RequestBody PayCnd payCnd, HttpServletRequest request) {
        log.debug("支付请求参数为：" + payCnd.toString());

        //获取用户某个商品下单成功次数
        Boolean orderCount = orderService.getByOrderCount(payCnd.getOrderId(),payCnd.getAppId());
        if(orderCount){
            throw new BusException("已达到今日限购次数");
        }
        // 数据一致性校验
        if (StringUtils.isEmpty(payCnd.getOrderId())) {
            validate(payCnd);
        }
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
        AppStatus appStatus = dealerAppInfoService.queryAppStatus(payCnd.getAppId());
        if (appStatus.getAppStatus() == null || appStatus.getAppStatus() == 0) {
            throw new BusException(ResultEnum.APP_FORBIDDEN.getMsg(),ResultEnum.APP_FORBIDDEN.getCode());
        }
        if (appStatus.getDealerAppStatus() == null) {
            throw new BusException(ResultEnum.APP_NOT_EXIST.getMsg());
        }
        if (appStatus.getDealerAppStatus() != 1) {
            throw new BusException(ResultEnum.APP_OFF_SHELVES.getMsg(),ResultEnum.APP_OFF_SHELVES.getCode());
        }
        // 根据商品id获取商品价格信息
        List<DealerAppPriceInfo> dealerAppPriceInfoList = dealerAppPriceInfoService.queryAppPriceInfoByAppId(payCnd.getAppId(), payCnd.getAptId());
        if (dealerAppPriceInfoList == null || dealerAppPriceInfoList.isEmpty()) {
            throw new BusException(ResultEnum.APP_NOT_CONFIG_PRICE);
        }
        DealerAppPriceInfo appPriceInfo = null;
        DealerAppPriceInfo customAppPriceInfo = null;
        for (DealerAppPriceInfo dealerAppPriceInfo : dealerAppPriceInfoList) {
            if (dealerAppPriceInfo.getPrice().compareTo(payCnd.getTotalFee()) == 0) {
                appPriceInfo = dealerAppPriceInfo;
                break;
            }
            if (dealerAppPriceInfo.getIsCustom() == 1) {
                customAppPriceInfo = dealerAppPriceInfo;
            }
        }
        BigDecimal actualPayAmount;
        // 没有匹配的面值
        if (appPriceInfo == null) {
            if (customAppPriceInfo == null) {
                throw new BusException(ResultEnum.APP_NOT_CONFIG_PRICE);
            }
            // 自定义支付金额
            actualPayAmount = customAppPriceInfo.getCustomPayAmount(payCnd.getTotalFee());
            payCnd.validateTradeFee(actualPayAmount);
            payCnd.validateSupPrice(customAppPriceInfo.getPrice().divide(customAppPriceInfo.getSupPrice(), 2, BigDecimal.ROUND_UP));
        } else {
            // 和数据库中的面值匹配到了
            actualPayAmount = appPriceInfo.getActualPayAmount(payCnd.getDiscount());
            payCnd.validateTradeFee(actualPayAmount);
            payCnd.validateSupPrice(appPriceInfo.getPrice().divide(appPriceInfo.getSupPrice(), 2, BigDecimal.ROUND_UP));
        }
    }

    /**
     * <b>功能描述：</b>主动去查询订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("/updateOrderStatusByActiveQuery")
    @ApiOperation(httpMethod="POST" ,value = "主动去查询订单状态")
    public ApiResult updateOrderStatusByActiveQuery(@RequestBody CodeCnd codeCnd) {
        ApiResult<StatusVo> apiResult = new ApiResult<>();
        Order order = orderService.updateOrderStatusByActiveQuery(codeCnd.getOrderId());
        StatusVo statusVo = new StatusVo();
        statusVo.setStatus(order.getStatus());
        statusVo.setAppId(order.getAppId() + "");
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
        order.setAptId(payCnd.getAptId());
        order.setAppName(payCnd.getAppName());
        order.setRechargeMode(payCnd.getRechargeMode());
        // 交易状态为待支付
        order.setTradeStatus(Order.TradeStatusConst.WAIT_PAY);
        order.setDealerPrice(payCnd.getDealerPrice());
        order.setIsWxAuth(payCnd.getIsWxAuth());
        return order;
    }

}
