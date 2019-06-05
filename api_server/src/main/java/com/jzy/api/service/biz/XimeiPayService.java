package com.jzy.api.service.biz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface XimeiPayService extends PayService{
    void updateXimeiPayCallback(HttpServletRequest req, HttpServletResponse resp);

    void updateXimeiRefundCallback(HttpServletRequest req, HttpServletResponse resp);

    String refundTest(BigDecimal tradeFee, String orderNo);

    String queryOrder(String orderNo, String tranSerialNo);
}
