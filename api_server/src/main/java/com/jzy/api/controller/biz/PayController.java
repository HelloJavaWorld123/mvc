package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.PayCnd;
import com.jzy.api.cnd.biz.WaitPayCnd;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.PayService;
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

    /**
     * <b>功能描述：</b>等待支付，校验订单是否已经过期<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping(path = "/waitPay")
    public ApiResult waitPay(@RequestBody WaitPayCnd waitPayCnd) {
        log.debug("waitPay");
        if (waitPayCnd.getId() != null) {

        }
        throw new BusException("123456");
        // return new ApiResult();
    }

    /**
     * <b>功能描述：</b>请求支付下单,初始化/更新订单,调起微信/支付宝<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("/pay")
    public ApiResult pay(HttpServletRequest request, @RequestBody PayCnd payCnd) {
        ApiResult<String> apiResult = new ApiResult<>();
        Order order = new Order();
        String linkUrl = orderService.insertOrUpdateOrder(request, order);
        apiResult.setData(linkUrl);
        return apiResult.success();
    }

}
