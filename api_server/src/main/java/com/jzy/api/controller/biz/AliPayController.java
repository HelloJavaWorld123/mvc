package com.jzy.api.controller.biz;

import com.jzy.api.constant.SupConfig;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.TradeRecord;
import com.jzy.api.service.biz.AliPayService;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.util.AlipayUtil;
import com.jzy.api.util.MyHttp;
import com.jzy.framework.controller.GenericController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Map;

import static com.jzy.api.model.biz.TradeRecord.RecordConst.STATUS_FAILED;
import static com.jzy.api.model.biz.TradeRecord.RecordConst.STATUS_PASSED;

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
@Controller
public class AliPayController extends GenericController {

    @Resource
    private AliPayService aliPayService;

    @Resource
    private TradeRecordService tradeRecordService;

    @Resource
    private OrderService orderService;

    /**
     * <b>功能描述：</b>服务器异步通知页面路径<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("ali/notify.shtml")
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
            String status = rststatus ? STATUS_PASSED : STATUS_FAILED;
            tradeRecordService.updateBgRespByOperatorStatus(tradeNo, status, respMap.toString(), outTradeNo, TradeRecord.RecordConst.STATUS_WAITED);
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
