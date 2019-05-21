package com.jzy.api.service.biz.impl;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jzy.api.dao.biz.OrderMapper;
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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
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
    private SupService supService;

    @Resource
    private OrderService orderService;

    /**
     * <b>功能描述：</b>支付<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public ApiResult pay(HttpServletRequest request, Order order) {
        String subject = "玖佰充值商城" + "-" + order.getAppName();
        // 支付
        String url = AlipayUtil.tradeWapPay(order, subject);
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
        AlipayTradeRefundResponse aliRes = AlipayUtil.tradeRefund(order.getOutTradeNo(), order.getTradeCode(), order.getTradeFee());
        if (aliRes.isSuccess()) {
            order.setTradeStatus(Order.TradeStatusConst.REFUND_SICCESS);
            return true;
        }
        String errMsg = order.getId() + "订单支付宝申请退款失败:" + aliRes.getSubCode() + "/" + aliRes.getSubMsg();
        log.error(errMsg);
        return false;
    }

    /**
     * <b>功能描述：</b>获取订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int queryOrderStatus(Order order) {
        AlipayTradeQueryResponse aliResponse = AlipayUtil.tradeQuery(order.getOutTradeNo());
        String aliTradeStatus = aliResponse.getTradeStatus();
        log.debug("ali webapp_result,orderId=".concat(order.getOrderId()).concat(",out_trade_no=").concat(order.getOutTradeNo()).concat(",ali支付状态:") + aliTradeStatus);
        if (StringUtils.isEmpty(aliTradeStatus) ||
                AlipayUtil.TradeState.WAIT_BUYER_PAY.toString().equals(aliTradeStatus)) {
            return 0;
        }
        return 1;
//        if (AlipayUtil.TradeState.TRADE_SUCCESS.toString().equals(aliTradeStatus)
//                || AlipayUtil.TradeState.TRADE_FINISHED.toString().equals(aliTradeStatus)) {
//            return  2;
//        }
//        if (AlipayUtil.TradeState.TRADE_CLOSED.toString().equals(aliTradeStatus)) {
//            return  4;
//        }
//        return 0;
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
        if (!AlipayUtil.signatureValid(respMap)) {
            log.error("：：：Alipay notify result - faliure");
            aliReturn("failure", resp);
            return;
        }
        log.debug("：：：Alipay notify result - success");
        String outTradeNo = respMap.get("out_trade_no");
        String tradeNo = respMap.get("trade_no");
        String tradeStatus = respMap.get("trade_status");
        boolean isSuccess = "TRADE_SUCCESS".equals(tradeStatus);

        //String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
        String orderId = orderService.queryOrderIdByoutTradeNo(outTradeNo);

        Integer status = isSuccess ? 4 : 3;
        tradeRecordService.updateAliPayCallbackStatus(tradeNo, status, respMap.toString(), outTradeNo, 1);
        if (isSuccess) {
            supService.commitOrderToSup(orderId, tradeNo, new BigDecimal(respMap.get("total_amount")));
        }
        aliReturn("success", resp);
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
