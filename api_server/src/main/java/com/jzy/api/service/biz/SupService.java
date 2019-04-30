package com.jzy.api.service.biz;

import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.SupRecord;
import com.jzy.framework.service.BaseService;

import java.util.Map;

/**
 * <b>功能：</b>SUP业务相关<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface SupService extends BaseService {

    void commitOrderToSup(Order order) throws Exception;

    /**
     * <b>功能描述：</b>SUP充值接口数据生成<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    String orderReceive(Order order);

    /**
     * <b>功能描述：</b>根据订单id查询sup交易记录<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    SupRecord querySupRecordByOrderId(String orderId);

}
