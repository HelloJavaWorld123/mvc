package com.jzy.api.controller.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jzy.api.constant.SupConfig;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.SupRecord;
import com.jzy.api.service.biz.CardPwdService;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.SupService;
import com.jzy.api.util.MyEncrypt;
import com.jzy.framework.controller.GenericController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>SUP功能<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190429&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Controller
public class SupController extends GenericController {

    @Resource
    private OrderService orderService;

//    @Resource
//    private DealerService dealerService;

    @Resource
    private CardPwdService cardPwdService;

    @Resource
    private SupService supService;

    /**
     * <b>功能描述：</b>SUP下单异步通知<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @GetMapping("sup_receive_callback")
    public String supReceiveCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String outTradeNo = request.getParameter("userOrderId");
        String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
        Order order = orderService.queryOrderById(orderId);
        if (order == null) {
            log.debug("SUP订单异步通知order查询为null,userOrderId=" + outTradeNo);
            return null;
        }
        String sign = request.getParameter("sign");
        String status = request.getParameter("status");
        String supKey = null; //dealerService.queryByUseridOrDefault(order.getUserId() + "").getSupKey();
        // 返回消息字符转换
        String mes = convertCharset("mes", request);
        String kmInfo = convertCharset("kmInfo", request);
        String businessId = convertCharset("businessId", request);
        String payoffPriceTotal = convertCharset("payoffPriceTotal", request);
        String responseData = request.getRequestURL().toString()
                .concat("?businessId=").concat(businessId)
                .concat("&userOrderId=").concat(outTradeNo)
                .concat("&status=").concat(status)
                .concat("&mes=").concat(mes)
                .concat("&kmInfo=").concat(kmInfo)
                .concat("&payoffPriceTotal=").concat(payoffPriceTotal)
                .concat("&sign=").concat(sign);
        log.info("SUP订单异步通知请求参数: " + responseData);
        // md5明文
        String md5Data = businessId + outTradeNo + status + supKey;
        // md5密文
        String md5Sign = MyEncrypt.getInstance().md5(md5Data);
        if (!md5Sign.equals(sign)) {
            log.info("SUP订单异步通知验签失败,本地签名md5Sign=".concat(md5Sign).concat(",请求签名sign=").concat(sign));
            return null;
        }
        // 获取SUP充值记录
        SupRecord osr = new SupRecord(); // supService.getOrderId(order.getId());
        if(osr != null){
            try {
                osr.setPurchaserPrice(new BigDecimal(payoffPriceTotal));
            } catch (Exception e) {
                // 处理没有返回价格的情况
                osr.setPurchaserPrice(new BigDecimal(-1));
            }
            osr.setBgRespData(!StringUtils.isEmpty(osr.getBgRespData()) ? osr.getBgRespData().concat(";" + responseData) : responseData);
            osr.setBgRespMes(!StringUtils.isEmpty(osr.getBgRespMes()) ? osr.getBgRespMes().concat(";" + mes) : mes);
            osr.setBgRespTime(new Date());
            osr.setBgRespAmount(osr.getBgRespAmount() + 1);
            // iOrderSupRecordService.updateWhereOrderId(osr);
        }
        if (SupConfig.SUP_STATUS_01.equals(status)) {
            // 是否是卡密
            if (!StringUtils.isEmpty(kmInfo)) {
                JSONArray kmArray = JSONArray.parseArray(kmInfo);
                for (int i = 0; i < kmArray.size(); i++) {
                    JSONObject km = kmArray.getJSONObject(i);
//                    if (!cardPwdService.countOrderIdCardNo(order.getId(), km.get("cardNo").toString())) {
//                        cardPwdService.insert(new OrderCardkeyMapper(CommUtils.uniqueOrderStr(), order.getId(), km.get("cardNo").toString(), km.get("cardPwd").toString(), Double.valueOf(request.getParameter("payoffPriceTotal")), km.get("outDate").toString()));
//                    }
                }
            }
            // 更新支付状态
            // orderService.updateStatusTradeStatusSupStatus(order.getId(), 2, Order.TradeStatusConst.FINISHED,2);
        } else {
            // SUP充值失败，进行支付宝或微信退单
            // orderService.tradeRefund(order);
        }
        response.getWriter().write("<receive>ok</receive>");
        return null;
    }

    /**
     * <b>功能描述：</b>转换字符集<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private String convertCharset(String key, HttpServletRequest request) throws UnsupportedEncodingException {
        return new String(request.getParameter(key).getBytes("ISO-8859-1"),"UTF-8");
    }

}
