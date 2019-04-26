package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.GenericCnd;
import lombok.Data;

/**
 * <b>功能：</b>请求支付下单,初始化/更新订单,调起微信/支付宝<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class PayCnd extends GenericCnd {
    /**
     * 支付方式
     * 0微信,1支付宝
     */
    private Integer tradeMethod;
    /**
     * 该订单id的传参是从待支付页面传递过来的
     */
    private Long orderId;
}
