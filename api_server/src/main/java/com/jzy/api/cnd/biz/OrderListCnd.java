package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;

/**
 * <b>功能：</b>订单列表输入参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class OrderListCnd extends PageCnd {

    /**
     * 订单状态
     * 0：未支付 1：充值中 2：充值成功 3：充值失败 4：充值关闭
     */
        private Integer status;

}
