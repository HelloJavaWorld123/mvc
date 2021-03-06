package com.jzy.api.service.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jzy.api.constant.SupConfig;
import com.jzy.api.dao.biz.SupRecordMapper;
import com.jzy.api.model.biz.CardPwd;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.SupRecord;
import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.service.arch.DealerService;
import com.jzy.api.service.biz.*;
import com.jzy.api.service.wx.WXPayUtil;
import com.jzy.api.util.*;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class SupServiceImpl extends GenericServiceImpl<SupRecord> implements SupService {

    @Resource
    private SupRecordMapper supRecordMapper;

    @Resource
    private PaywayProvider paywayProvider;

    @Resource
    private OrderService orderService;

    @Resource
    private DealerService dealerService;

    @Resource
    private CardPwdService cardPwdService;

    @Value("${basic_site_dns}")
    private String domainUrl;

    @Value("${sup_callback_url}")
    private String supCallbackUrl;

    @Value("${sup_order_receive}")
    private String supOrderReceive;

    /**
     * <b>功能描述：</b>提交订单到SUP<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void commitOrderToSup(String orderId, String transactionId, BigDecimal payTotalFee) {
        Order order = orderService.queryOrderById(orderId);
        if (order.getSupStatus() != 0) {
            return;
        }
        order.setStatus(1);
        order.setSupStatus(0);
        order.setTradeCode(order.getTradeCode());
        order.setTradeFee(order.getTradeFee());
        order.setTradeStatus(Order.TradeStatusConst.PAY_SUCCESS);
        order.setPayTime(new Date());
        order.setCode(transactionId);
        orderService.update(order);
        // 构造请求SUP参数
        String requestData = buildRequestParam(order);
        // 向SUP发起请求
        String resultXml = MyHttp.sendPost(requestData, null);
        log.debug("提交SUP订单同步返回,订单id:".concat(order.getOrderId()).concat("返回结果:").concat(resultXml));
        Map<String, String> resultMap ;
        try {
            resultMap = WXPayUtil.xmlToMap(resultXml);
        } catch (Exception e) {
            log.error("SUP返回结果解析异常", e);
            return;
        }
        String resultCode = resultMap.get("result");
        // sup同步返回成功
        if (SupConfig.SUP_STATUS_01.equals(resultCode)) {
            orderService.updateSupStatus(order.getOrderId(), 1);
            //修改一下sup返回的备注信息,避免和异步返回备注信息相同都是成功。
            resultMap.put("mes","充值中");
        } else {
            // sup同步返回失败，退单
           orderService.tradeRefund(order);
        }
        // SUP充值记录
        SupRecord supRecord = supRecordMapper.querySupRecordByOrderId(order.getOrderId());
        if (supRecord == null) {
            supRecordMapper.insert(new SupRecord(CommUtils.uniqueOrderStr(), order.getOrderId(),
                    requestData, new Date(), 1, JSON.toJSONString(resultMap),resultMap.get("mes")));
        } else {
            supRecord.setReqAmount(supRecord.getReqAmount() + 1);
            supRecord.setReqTime(new Date());
            supRecord.setReqData(supRecord.getReqData() + ";" + requestData);
            supRecord.setRemark(resultMap.get("mes"));
            supRecordMapper.update(supRecord);
        }
    }

    /**
     * <b>功能描述：</b>更新sup回调<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateSupCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String outTradeNo = request.getParameter("userOrderId");
        //String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
        String orderId = orderService.queryOrderIdByoutTradeNo(outTradeNo);

        Order order = orderService.queryOrderById(orderId);
        if (order == null) {
            log.debug("SUP订单异步通知order查询为null,userOrderId=" + outTradeNo);
            response.getWriter().write("<receive>ok</receive>");
            return;
        }
        if (order.getSupStatus() == 2 || order.getSupStatus() == 3) {
            log.debug("SUP订单异步通知sup订单状态已经为成功或失败,userOrderId=" + outTradeNo);
            response.getWriter().write("<receive>ok</receive>");
            return;
        }
        String sign = request.getParameter("sign");
        String status = request.getParameter("status");
        String supKey = dealerService.queryDealer(order.getDealerId()).getSupKey();
        String mes = request.getParameter("mes");
//        String kmInfo = convertCharset("kmInfo", request);
        String kmInfo = request.getParameter("kmInfo");
        String businessId = convertCharset("businessId", request);
        String payoffPriceTotal = convertCharset("payoffPriceTotal", request);
        String responseData = request.getRequestURL().toString()
                .concat("?businessId=").concat(businessId)
                .concat("&userOrderId=").concat(outTradeNo)
                .concat("&status=").concat(status)
                .concat("&mes=").concat(mes)
                .concat("&payoffPriceTotal=").concat(payoffPriceTotal)
                .concat("&sign=").concat(sign);
        if (StringUtils.isEmpty(kmInfo)) {
            responseData = responseData.concat("&kmInfo=");
        }else {

            responseData = responseData.concat("&kmInfo="+kmInfo);
        }

        // 获取SUP充值记录
        SupRecord supRecord = querySupRecordByOrderId(order.getOrderId());
        //更新sup回调记录
        if (supRecord != null) {
            supRecord.setPurchaserPrice(!StringUtils.isEmpty(payoffPriceTotal) ? new BigDecimal(payoffPriceTotal) : BigDecimal.ZERO);
            supRecord.setBgRespData(!StringUtils.isEmpty(supRecord.getBgRespData()) ? supRecord.getBgRespData().concat(";" + responseData) : responseData);
            supRecord.setBgRespMes(!StringUtils.isEmpty(supRecord.getBgRespMes()) ? supRecord.getBgRespMes().concat(";" + mes) : mes);
            supRecord.setBgRespTime(new Date());
            supRecord.setRemark(mes);
            supRecord.setBgRespAmount(supRecord.getBgRespAmount() + 1);
            supRecordMapper.update(supRecord);
        }

        log.debug("SUP订单异步通知请求参数: " + responseData);
        // md5明文
        String md5Data = businessId + outTradeNo + status + supKey;
        // md5密文
        String md5Sign = MyEncrypt.getInstance().md5(md5Data);

        if (!md5Sign.equals(sign)) {
            log.debug("SUP订单异步通知验签失败,本地签名md5Sign=".concat(md5Sign).concat(",请求签名sign=").concat(sign));
            //微信或支付宝退单
            //orderService.tradeRefund(order);
            orderService.updateStatusTradeStatusSupStatus(order.getOrderId(), 3, Order.TradeStatusConst.REFUND_SICCESS,3);
            response.getWriter().write("<receive>error</receive>");
            return;
        }

        if (SupConfig.SUP_STATUS_01.equals(status)) {
            // 是否是卡密
            if (!StringUtils.isEmpty(kmInfo)) {
                JSONArray kmArray = JSONArray.parseArray(kmInfo);
                for (int i = 0; i < kmArray.size(); i++) {
                    JSONObject km = kmArray.getJSONObject(i);
//                    if (!cardPwdService.isExist(order.getOrderId(), km.get("cardNo").toString())) {
                    CardPwd cardPwd = new CardPwd();
                    cardPwd.setCardPwdId(CommUtils.uniqueOrderStr());
                    cardPwd.setOrderId(order.getOrderId());
                    cardPwd.setCardNo(km.get("cardNo").toString());
                    cardPwd.setCardPwd(km.get("cardPwd").toString());
                    cardPwd.setPayoffPriceTotal(supRecord.getPurchaserPrice());
                    cardPwd.setGmtExpired(km.get("outDate").toString());
                    cardPwdService.insert(cardPwd);
//                    }
                }
            }
            // 更新支付状态
            orderService.updateStatusTradeStatusSupStatus(order.getOrderId(), 2, Order.TradeStatusConst.PAY_SUCCESS,2);
        } else {
            // SUP充值失败，进行支付宝或微信退单
            orderService.tradeRefund(order);
        }
        response.getWriter().write("<receive>ok</receive>");
    }

    /**
     * <b>功能描述：</b>退单<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private boolean tradeRefund(Order order) {
        PayService payService = paywayProvider.getPayService(order.getTradeMethod());
        return payService.orderBack(order);
    }

    /**
     * <b>功能描述：</b>根据订单id查询sup交易记录<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    @Override
    public SupRecord querySupRecordByOrderId(String orderId) {
        return supRecordMapper.querySupRecordByOrderId(orderId);
    }

    /**
     * <b>功能描述：</b>构造请求sup参数<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private String buildRequestParam(Order order) {
        Dealer dealer = dealerService.queryDealer(order.getDealerId());
        String supBusinessId =  dealer.getSupBusinessid();
        String supKey = dealer.getSupKey();

        // md5明文
        String md5Data = supBusinessId + order.getOutTradeNo() + order.getSupNo() + order.getSupPrice() + "" + supKey;
        // md5密文
        String sign = MyEncrypt.getInstance().md5(md5Data);
        StringBuilder urlData = new StringBuilder();
        String area = order.getGameArea();
        String serv = order.getGameServ();
        String gameArea = "";
        if (!StringUtils.isEmpty(area)) {
            if (!StringUtils.isEmpty(serv)) {
                gameArea = area.concat("|").concat(serv);
            } else {
                gameArea = area;
            }
        } else {
            if (!StringUtils.isEmpty(serv)) {
                gameArea = serv;
            }
        }
        urlData.append("businessId=").append(supBusinessId)
                .append("&userOrderId=").append(order.getOutTradeNo())
                .append("&goodsId=").append(order.getSupNo())
                .append("&userName=").append(StringUtils.isEmpty(order.getAccount())?"000000":order.getAccount())
                .append("&gameName=").append(order.getAppName())
                .append("&gameAcct=").append(order.getGameAccount())
                .append("&gameArea=").append(gameArea)
                .append("&gameType=").append(order.getPriceTypeName())
                .append("&acctType=").append(order.getAcctType())
                .append("&goodsNum=").append(order.getSupPrice())
                .append("&noticeUrl=").append(this.domainUrl.concat(supCallbackUrl))
                .append("&sign=").append(sign);
        String reqUrl = this.supOrderReceive + urlData.toString();
        log.debug("sup充值提交请求:{}", reqUrl);
        return reqUrl;
    }

    /**
     *
     */


    /**
     * <b>功能描述：</b>转换字符集<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private String convertCharset(String key, HttpServletRequest request) throws UnsupportedEncodingException {
        String value = request.getParameter(key);
        return new String(value.getBytes("ISO-8859-1"),"UTF-8");
    }

    /**
     * <b>功能描述：</b>根据订单id查询sup金额和返回备注<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     */
    @Override
    public SupRecord queryPurchaserPriceAndRemarkByOrderId(String orderId) {
        return supRecordMapper.queryPurchaserPriceAndRemarkByOrderId(orderId);
    }

    @Override
    protected GenericMapper<SupRecord> getGenericMapper() {
        return supRecordMapper;
    }
}
