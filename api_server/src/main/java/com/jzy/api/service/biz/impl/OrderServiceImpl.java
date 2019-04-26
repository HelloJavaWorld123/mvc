package com.jzy.api.service.biz.impl;

import com.jzy.api.dao.biz.OrderMapper;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.OrderService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;

import javax.annotation.Resource;

/**
 * <b>功能：</b>订单业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class OrderServiceImpl extends GenericServiceImpl<Order> implements OrderService {

    @Resource
    private OrderMapper orderMapper;



    @Override
    protected GenericMapper getGenericMapper() {
        return orderMapper;
    }
}
