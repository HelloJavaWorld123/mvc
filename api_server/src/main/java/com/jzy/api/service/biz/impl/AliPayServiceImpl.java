package com.jzy.api.service.biz.impl;

import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.AliPayService;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.util.AlipayUtil;
import com.jzy.framework.result.ApiResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>功能：</b>微信支付业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190419&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    @Override
    public ApiResult pay(Order order) {
        // 构造支付请求参数
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", order.getOutTradeNo());
        data.put("total_amount", order.getTotalFee().toString());
        data.put("subject", "玖佰充值商城" + "-" + order.getAppName());
        // 支付
        String url = AlipayUtil.tradeWapPay(data);
        // 支付返回参数
        Map<String, String> payMap = new HashMap<>();
        payMap.put("alipayUrl", url);
        payMap.put("tradeMethod", "1");
//        payMap.put("aliWebappReturnUrl", iConfigService.domainUrl().concat("/pay/ali/webapp_return.shtml?orderId=".concat(order.getId())));
        return new ApiResult<>(payMap);
    }
}
