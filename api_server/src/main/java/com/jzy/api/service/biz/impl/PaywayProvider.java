package com.jzy.api.service.biz.impl;

import com.jzy.api.service.biz.PayService;

import java.util.Map;

public class PaywayProvider {

    private Map<Integer, PayService> payWayMap;

    public PayService getPayService(int payWayId) {
        return payWayMap.get(payWayId);
    }

    public void setPayWayMap(Map<Integer, PayService> payWayMap) {
        this.payWayMap = payWayMap;
    }

}
