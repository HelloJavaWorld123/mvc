package com.jzy.api.controller.biz;

import com.alibaba.fastjson.JSONObject;
import com.jzy.api.constant.SupConfig;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.util.AlipayUtil;
import com.jzy.api.util.MyHttp;
import com.jzy.framework.controller.GenericController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
    private TradeRecordService tradeRecordService;

    @Resource
    private OrderService orderService;

    /**
     * alipay:页面跳转同步通知页面路径
     * @return
     */
    @RequestMapping("/return.shtml")
    public ModelAndView aliRetrunUrl(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> respMap = MyHttp.currentreadforMap(req);
        ModelAndView view = new ModelAndView();
        log.info("：：：Alipay return result - " + respMap);
        if (AlipayUtil.signatureValid(respMap)) {
            log.info("：：：Alipay return result - success");
            String outTradeNo = respMap.get("out_trade_no");
            String tradeNo = respMap.get("trade_no");
            tradeRecordService.updateRespByOperatorStatus(tradeNo, 1, respMap.toString(), outTradeNo, 1);
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            Order order = orderService.queryOrderByOutTradeNo(outTradeNo);
            view = synchroSuccessPay(order, 1);
        } else {
            log.info("：：：Alipay return result - faliure");
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            view.setViewName("/order/result/fail");
        }
        return view;
    }

    /**
     * <b>功能描述：</b>服务器异步通知页面路径<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping(path = "/notify.shtml")
    public void aliNotifyUrl(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> respMap = MyHttp.currentreadforMap(req);
        log.info("：：：Alipay notify result - " + respMap);
        if (AlipayUtil.signatureValid(respMap)) {
            log.info("：：：Alipay notify result - success");
            String outTradeNo = respMap.get("out_trade_no");
            String tradeNo = respMap.get("trade_no");
            String tradeStatus = respMap.get("trade_status");
            boolean rststatus = "TRADE_SUCCESS".equals(tradeStatus);
            String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
            Integer status = rststatus ? 4 : 3;
            tradeRecordService.updateRespByOperatorStatus(tradeNo, status, respMap.toString(), outTradeNo, 1);
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            if (rststatus) {
                notifySuccessPay(orderId ,tradeNo ,Double.valueOf(respMap.get("total_amount")));
            }

            aliReturn("success", resp);
        } else {
            log.info("：：：Alipay notify result - faliure");
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            aliReturn("failure", resp);
        }
    }

    /**
     * 同步支付调用
     * @param order  订单
     * @param status 更新的订单状态
     * @return view /order/result/rechange.jsp
     */
    public ModelAndView synchroSuccessPay(Order order, Integer status) {
        JSONObject vI = JSONObject.parseObject("{\"1\":\"/order/result/rechange\",\"2\":\"/order/result/success\",\"3\":\"/order/result/fail\",\"4\":\"/order/result/close\"}");
        String vV = vI.getString(String.valueOf(status));
        String vi = StringUtils.isEmpty(vV) ? vI.getString("1") : vV;
        ModelMap modelMap = new ModelMap();
        modelMap.put("ai_id", "dealer/app/cate_index");
        ModelAndView view = new ModelAndView(vi, modelMap);
        try {
            if (order != null) {
                if (order.getStatus() == 0 && status != 0) {
                    orderService.updateStatus(order.getOrderId(), status);
                }
                modelMap.put("ai_id", "sup/get_product_info/".concat(order.getAppId() + ""));
                modelMap.put("orderId", order.getOrderId());
                view = new ModelAndView(vi, modelMap);
            }
        } catch (Exception e) {
            log.error("同步支付调用发生异常", e);
        }
        return view;
    }

    /**
     * 异步支付成功给sup发送订单请求
     * @param orderId 订单id
     * @param transactionId 支付系统返回的交易
     * @param payTotalFee 支付系统返回的实付金额
     * @return 01:SUP订单提交成功, 02:SUP订单提交失败 , 03:订单已提交,重复订单
     */
    private String notifySuccessPay(String orderId, String transactionId, Double payTotalFee) {
        String resultCode = null;
        try {
            Order order = orderService.queryOrderById(orderId);
            if (order.getSupStatus() != 0) {
                return SupConfig.SUP_STATUS_03;
            }
            order.setStatus(1);
            order.setTradeCode(transactionId);
            order.setTradeFee(new BigDecimal(payTotalFee));
            order.setTradeStatus(Order.TradeStatusConst.PAY_SUCCESS);
            orderService.update(order);
            // 提交订单到SUP
            // orderService.orderReceiveSup(order);
        } catch (Exception e) {
            log.error("支付成功订单提交失败,订单id:".concat(orderId).concat("异常信息:"), e);
        }
        return resultCode;
    }

    /**
     * <b>功能描述：</b>回调通知反馈<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void aliReturn(String return_code, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=" + AlipayUtil.CHARSET);
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.write(return_code);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

}
