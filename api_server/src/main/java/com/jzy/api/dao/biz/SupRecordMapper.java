package com.jzy.api.dao.biz;

import com.jzy.api.model.biz.SupRecord;
import com.jzy.framework.dao.GenericMapper;

/**
 * <b>功能：</b>sup充值处理mapper<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface SupRecordMapper extends GenericMapper<SupRecord> {

    /**
     * <b>功能描述：</b>根据订单id查询sup交易记录<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    SupRecord querySupRecordByOrderId(String orderId);

    /**
     * <b>功能描述：</b>根据订单id查询sup金额和返回备注<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    SupRecord queryPurchaserPriceAndRemarkByOrderId(String orderId);

}
