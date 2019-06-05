package com.jzy.api.controller.biz;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.service.biz.AliPayService;
import com.jzy.api.service.biz.XimeiPayService;
import com.jzy.framework.controller.GenericController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(path = "/ximei")
public class XimeiPayController extends GenericController {

    @Resource
    private XimeiPayService ximeiPayService;

    /**
     * <b>功能描述：</b>支付宝异步通知支付结果<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @WithoutLogin
    @RequestMapping(path = "/payCallback")
    public void payCallback(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("payCallback");
        // 处理西煤支付回调结果
        ximeiPayService.updateXimeiPayCallback(req, resp);
    }

    @WithoutLogin
    @RequestMapping(path = "/refundCallback")
    public void refundCallback(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("refundCallback");
        // 处理西煤支付退款回调结果
        ximeiPayService.updateXimeiRefundCallback(req, resp);
    }

    @WithoutLogin
    @RequestMapping(path = "/queryOrder")
    public String queryOrder(@RequestParam(value="orderNo") String orderNo,@RequestParam(value="tranSerialNo") String tranSerialNo) {
        log.debug("---ximei---queryOrder");
        // 查询支付结果
        String result = ximeiPayService.queryOrder(orderNo,tranSerialNo);
        return result;
    }
    @WithoutLogin
    @RequestMapping("/refundTest")
    public String refundTest(@RequestParam(value="tradeFee") BigDecimal tradeFee, @RequestParam(value="orderNo") String orderNo){
        String result = ximeiPayService.refundTest(tradeFee,orderNo);
        return result;
    }



}
