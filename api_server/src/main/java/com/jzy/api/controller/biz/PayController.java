package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.PayCnd;
import com.jzy.api.cnd.biz.WatiPayCnd;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class PayController extends GenericController {

    /**
     * <b>功能描述：</b>等待支付，校验订单是否已经过期<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping
    public ApiResult waitPay(@RequestBody WatiPayCnd watiPayCnd) {

        return null;
    }

    /**
     * <b>功能描述：</b>请求支付下单,初始化/更新订单,调起微信/支付宝<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping
    public ApiResult pay(@RequestBody PayCnd payCnd) {

        return null;
    }

}
