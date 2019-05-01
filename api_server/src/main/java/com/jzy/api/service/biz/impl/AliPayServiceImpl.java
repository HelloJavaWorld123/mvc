package com.jzy.api.service.biz.impl;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.TradeRecord;
import com.jzy.api.service.biz.AliPayService;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.SupService;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.util.AlipayUtil;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyHttp;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
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
@Slf4j
@Service
public class AliPayServiceImpl implements AliPayService {

    @Value("${basic_site_dns}")
    private String domainUrl;

    @Value("${ali_pay_url}")
    private String aliPayUrl;

    @Resource
    private TradeRecordService tradeRecordService;

    @Resource
    private OrderService orderService;

    @Resource
    private SupService supService;

    /**
     * <b>功能描述：</b>支付<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public ApiResult pay(HttpServletRequest request, Order order) {
        String subject = "玖佰充值商城" + "-" + order.getAppName();
        // 支付
        String url = AlipayUtil.tradeWapPay(order.getOutTradeNo(), order.getTradeFee(), subject);
        // 新增交易记录
        TradeRecord tradeRecord = new TradeRecord();
        String tradeRecordId = CommUtils.lowerUUID();
        tradeRecord.setTradeRecordId(tradeRecordId);
        tradeRecord.setOperator(order.getOutTradeNo());
        tradeRecord.setReqTime(new Date());
        tradeRecord.setReqUrl(aliPayUrl.concat("?method=").concat("alipay.trade.wap.pay"));
        tradeRecord.setReqData(url);
        tradeRecord.setStatus(1);
        tradeRecord.setType(0);
        tradeRecord.setTrusteeship(1);
        tradeRecordService.insert(tradeRecord);
        // 支付返回参数
        Map<String, String> payMap = new HashMap<>();
        payMap.put("alipayUrl", url);
        payMap.put("tradeMethod", "1");
        payMap.put("aliWebappReturnUrl", domainUrl.concat("/pay/ali/webapp_return.shtml?orderId=".concat(order.getOrderId())));
        ApiResult<String> apiResult = new ApiResult<>();
        apiResult.setData(url);
        return apiResult;
    }

    /**
     * <b>功能描述：</b>退单<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public boolean orderBack(Order order) {
        order.setStatus(3);
        order.setSupStatus(3);
        AlipayTradeRefundResponse aliRes = AlipayUtil.tradeRefund(order.getOutTradeNo(), order.getTradeCode(), order.getTotalFee());
        if (aliRes.isSuccess()) {
            order.setTradeStatus(Order.TradeStatusConst.REFUND_SICCESS);
        } else {
            log.error(order.getId() + "订单支付宝申请退款失败:" + aliRes.getSubCode() + "/" + aliRes.getSubMsg());
        }
        return false;
    }

    /**
     * <b>功能描述：</b>处理支付宝回调结果<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateAliPayCallback(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> respMap = MyHttp.currentreadforMap(req);
        log.debug("：：：Alipay notify result - " + respMap);
        if (AlipayUtil.signatureValid(respMap)) {
            log.info("：：：Alipay notify result - success");
            String outTradeNo = respMap.get("out_trade_no");
            String tradeNo = respMap.get("trade_no");
            String tradeStatus = respMap.get("trade_status");
            boolean isSuccess = "TRADE_SUCCESS".equals(tradeStatus);
            String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
            Integer status = isSuccess ? 4 : 3;
            tradeRecordService.updateAliPayCallbackStatus(tradeNo, status, respMap.toString(), outTradeNo, 1);
            if (isSuccess) {
                notifySuccessPay(orderId ,tradeNo , new BigDecimal(respMap.get("total_amount")));
            }
            aliReturn("success", resp);
        } else {
            log.info("：：：Alipay notify result - faliure");
            aliReturn("failure", resp);
        }
    }
    
    /**
     * <b>功能描述：</b>异步支付成功给sup发送订单请求<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     * @param transactionId 支付系统返回的交易
     * @param payTotalFee 支付系统返回的实付金额
     * @return 01:SUP订单提交成功, 02:SUP订单提交失败 , 03:订单已提交,重复订单
     */
    private void notifySuccessPay(String orderId, String transactionId, BigDecimal payTotalFee) {
        Order order = orderService.queryOrderById(orderId);
        if (order.getSupStatus() != 0) {
            return;
        }
        order.setStatus(1);
        order.setTradeCode(transactionId);
        order.setTradeFee(payTotalFee);
        order.setTradeStatus(Order.TradeStatusConst.PAY_SUCCESS);
        orderService.update(order);
        // 提交订单到SUP
        try {
            supService.commitOrderToSup(order);
        } catch (Exception e) {
            log.error("提交订单到SUP失败，异常信息为：", e);
        }
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
