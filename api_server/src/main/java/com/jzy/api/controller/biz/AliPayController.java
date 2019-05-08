package com.jzy.api.controller.biz;

import com.alibaba.fastjson.JSONObject;
import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.AliPayService;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.util.AlipayUtil;
import com.jzy.api.util.MyHttp;
import com.jzy.framework.controller.GenericController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <b>功能：</b>支付宝支付<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(path = "/ali")
public class AliPayController extends GenericController {

    @Resource
    private OrderService orderService;

    @Resource
    private AliPayService aliPayService;

    @Resource
    private TradeRecordService tradeRecordService;

    /**
     * <b>功能描述：</b>支付宝异步通知支付结果<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @WithoutLogin
    @RequestMapping(path = "/payCallback")
    public void payCallback(HttpServletRequest req, HttpServletResponse resp) {
        // 处理支付宝回调结果
        aliPayService.updateAliPayCallback(req, resp);
    }

}
