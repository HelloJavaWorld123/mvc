package com.jzy.api.service.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jzy.api.constant.PayConfig;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.TradeRecord;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.SupService;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.service.biz.XimeiPayService;
import com.jzy.api.service.wx.WXPayUtil;
import com.jzy.api.util.CommUtils;
import com.jzy.framework.result.ApiResult;
import com.rrtx.mer.bean.ProcessMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassNameName XimeiPayServiceImpl
 * @Description 西煤支付
 * @Author jiazhiyi
 * @DATE 2019/6/2
 * @Version 1.0
 **/
@Slf4j
@Service
public class XimeiPayServiceImpl implements XimeiPayService {


    @Resource
    private TradeRecordService tradeRecordService;
    @Resource
    private OrderService orderService;
    @Resource
    private SupService supService;


    /** 提交西煤支付
     * @Description
     * @Author lchl
     * @Date 2019/6/5 5:26 PM
     * @param request
     * @param order
     * @return com.jzy.framework.result.ApiResult
     */
    @Override
    public ApiResult pay(HttpServletRequest request, Order order) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("outTradeNo",order.getOutTradeNo());
        paramsMap.put("trusteeship",2);
        String tradeRecordId = tradeRecordService.queryIdByParams(paramsMap);
        log.debug("--XimeiPayServiceImpl-参数--"+order.getOutTradeNo()+"---tradeRecordId--"+tradeRecordId);
        // 支付
        String url = tradeWapPay(order);
        log.debug("--XimeiPayServiceImpl-pay---"+url);
        if(null!=tradeRecordId && !"".equals(tradeRecordId)){
        }else{
            // 新增交易记录
            TradeRecord tradeRecord = new TradeRecord();
            tradeRecordId = CommUtils.lowerUUID();
            tradeRecord.setTradeRecordId(tradeRecordId);
            tradeRecord.setOperator(order.getOutTradeNo());
            tradeRecord.setReqTime(new Date());
            tradeRecord.setReqUrl(wapUrl);
            tradeRecord.setReqData(url);
            tradeRecord.setStatus(1);
            tradeRecord.setType(0);
            tradeRecord.setTrusteeship(2);
            log.debug("--pay---tradeRecord"+JSON.toJSONString(tradeRecord));
            tradeRecordService.insert(tradeRecord);
        }
        ApiResult<String> apiResult = new ApiResult<>();
        Map<String, String> otherMap = new HashMap<>();
        otherMap.put("url",url);
        otherMap.put("orderId",order.getOrderId());
        apiResult.success(JSONObject.toJSONString(otherMap));
        return apiResult;
    }
    /** 西煤支付回调
     * @Description
     * @Author lchl
     * @Date 2019/6/5 3:38 PM
     * @param req
     * @param resp
     * @return void
     */
    @Override
    public void updateXimeiPayCallback(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("--XimeiPayServiceImpl-updateXimeiPayCallback-接收到支付结果通知---");

        String tranData=req.getParameter("tranData");
        String signData=req.getParameter("signData");

        log.debug("--XimeiPayServiceImpl-updateXimeiPayCallback-tranData--"+tranData);
        log.debug("--XimeiPayServiceImpl-updateXimeiPayCallback-signData--"+signData);

        String tranDataXML = new String(ProcessMessage.Base64Decode(tranData));
        log.debug("--XimeiPayServiceImpl-updateXimeiPayCallback-tranDataXML--"+tranDataXML);
        try {
            Map<String,String> resultMap = WXPayUtil.xmlToMap(tranDataXML);
            log.debug("-XimeiPayServiceImpl-updateXimeiPayCallback-resultMap-"+JSON.toJSONString(resultMap));
            String outTradeNo = resultMap.get("orderNo");

            String tradeNo = resultMap.get("tranFlowNo");
            //
            //文档错误是tranStat而不是orderStatus String orderStatus = resultMap.get("orderStatus");
            String orderStatus = resultMap.get("tranStat");
            //已支付
            boolean isSuccess = "1".equals(orderStatus);
            String orderId = orderService.queryOrderIdByoutTradeNo(outTradeNo);

            Integer status = isSuccess ? 4 : 3;
            tradeRecordService.updateXimeiPayCallbackStatus(tradeNo, status, JSON.toJSONString(resultMap), outTradeNo, 1);
            if (isSuccess) {
                //文档错误不是totalamt而是orderAmt
                supService.commitOrderToSup(orderId, tradeNo, new BigDecimal(resultMap.get("orderAmt")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("--XimeiPayServiceImpl-updateXimeiPayCallback--xmlToMap-"+e);
        }
    }

    /** 西煤退款
     * @Description
     * @Author lchl
     * @Date 2019/6/5 3:33 PM
     * @param order
     * @return boolean
     */
    @Override
    public boolean orderBack(Order order) {
        //0待支付，1支付中，2支付成功，3退款中，4充值关闭，5退款成功
        order.setStatus(3);//退款中
        //0未提交,1已提交,2充值成功，3充值失败
        order.setSupStatus(3);

        String responseStr = doRefundRequest(order);
        log.debug("--XimeiPayServiceImpl-orderBack--doRefundRequest---"+responseStr);
        if(null!=responseStr && !"".equals(responseStr)){
            Map<String, String> responseMap = (Map<String, String>) JSON.parse(new String(ProcessMessage.Base64Decode(responseStr)));
            //String tranDataXML = new String(ProcessMessage.Base64Decode(result));
            log.debug("--XimeiPayServiceImpl-orderBack-result-"+JSON.toJSONString(responseMap));

            String returnCode = responseMap.get("returnCode");
            String returnMessage = responseMap.get("returnMessage");

            if((null!=returnCode && !"".equals(returnCode))||(null!=returnMessage && !"".equals(returnMessage))){
                log.info("-退款错误-returnCode--"+returnCode+"---returnMessage---"+returnMessage);
            }else{
                String tranDataXml = new String(ProcessMessage.Base64Decode(responseMap.get("tranData")));

                try {
                    Map<String, String> resultMap = WXPayUtil.xmlToMap(tranDataXml);
                    log.debug("-XimeiPayServiceImpl-orderBack-resultMap-"+JSON.toJSONString(resultMap));

                    String outTradeNo = resultMap.get("orderNo");
                    String tradeNo = resultMap.get("tranSerialNo");
                    String orderStatus = resultMap.get("tranStat");
                    boolean orderStatusB = "31".equals(orderStatus);

                    tradeRecordService.insert(new TradeRecord(CommUtils.lowerUUID(),tradeNo,outTradeNo, new Date(), refundUrl,
                            JSON.toJSONString(order), orderStatusB?4:1,1, new Date(), JSON.toJSONString(resultMap),2));
                    if(orderStatusB){
                        order.setTradeStatus(Order.TradeStatusConst.REFUND_SICCESS);
                        return true;

                    }else{
                        order.setTradeStatus(Order.TradeStatusConst.WAIT_REFUND);
                        //order.setStatus(6);//待退款
                        //order.setSupStatus(3);//sup 充值失败
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("西煤支付-tranDataXml解析错误---");
                }
                //Map<String, String> resultMap = (Map<String, String>) JSON.parse(new String(ProcessMessage.Base64Decode(responseMap.get("tranData"))));

            }

        }else{
            log.error("西煤支付退款错误");
        }
        return false;
    }

    /** 查询订单状态
     * @Description
     * @Author lchl
     * @Date 2019/6/5 3:34 PM
     * @param order
     * @return int
     */
    @Override
    public int queryOrderStatus(Order order) {
        String resultXML = doQueryRequest(order,false);
        if(null!=resultXML && !"".equals(resultXML)){

            Map<String, String> resultMap = (Map<String, String>) JSON.parse(new String(ProcessMessage.Base64Decode(resultXML)));
            log.debug(JSON.toJSONString(resultMap));
            String returnCode = resultMap.get("returnCode");
            String returnMessage = resultMap.get("returnMessage");
            // 有时候returnCode 为空 只能判断returnMessage
            if((null!=returnCode && !"".equals(returnCode)) || (null!=returnMessage && !"".equals(returnMessage))){
            }else{
                String tranDataStr = new String(ProcessMessage.Base64Decode(resultMap.get("tranData")));
                Map<String, String> tranDataMap = (Map<String, String>) JSON.parse(tranDataStr);
                log.debug(JSON.toJSONString(tranDataMap));
                String tranStat = resultMap.get("tranStat");
                if ("1".equals(tranStat)){
                    return 1;
                }
            }
        }
        return 0;
    }

    /** 西煤暂时不支持 退款结果回调通知
     * @Description
     * @Author lchl
     * @Date 2019/6/5 3:35 PM
     * @param req
     * @param resp
     * @return void
     */
    @Override
    public void updateXimeiRefundCallback(HttpServletRequest req, HttpServletResponse resp) {

        log.debug("--XimeiPayServiceImpl-updateXimeiRefundCallback-接收到退款结果通知---");
        String tranData=req.getParameter("tranData");
        String signData=req.getParameter("signData");

        log.debug("--XimeiPayServiceImpl-updateXimeiRefundCallback-tranData--"+tranData);
        log.debug("--XimeiPayServiceImpl-updateXimeiRefundCallback-tranData--"+signData);

        Map<String, String> resultMap = (Map<String, String>) JSON.parse(new String(ProcessMessage.Base64Decode(tranData)));
        log.debug("--XimeiPayServiceImpl-updateXimeiRefundCallback-resultMap--"+JSON.toJSONString(resultMap));

        String outTradeNo = resultMap.get("orderNo");

        String tranSerialNo = resultMap.get("tranSerialNo");
        String tranStat = resultMap.get("tranStat");
        //退款成功
        boolean isSuccess = "1".equals(tranStat);

        String orderId = orderService.queryOrderIdByoutTradeNo(outTradeNo);
        tradeRecordService.updateXimeiRefundCallbackStatus(tranSerialNo, isSuccess?4:3, JSON.toJSONString(resultMap), outTradeNo);
        if(isSuccess){
            orderService.updateTradeStatus(orderId, Order.TradeStatusConst.REFUND_SICCESS);
        }
    }






    @Value("${xm.wapUrl}")
    private String wapUrl;

    @Value("${xm.merchantId}")
    private String merchantId;

    @Value("${xm.refundUrl}")
    private String refundUrl;

    @Value("${xm.returnUrl}")
    private String returnUrl;

    @Value("${xm.notifyUrl}")
    private String notifyUrl;

    @Value("${xm.keyPath}")
    private String strRealPath;

    @Value("${xm.keyPassword}")
    private String keyPassword;

    @Value("${xm.payInterfaceName}")
    private String payInterfaceName;

    @Value("${xm.refundNotifyUrl}")
    private String refundNotifyUrl;

    @Value("${xm.refundOrderInterfaceName}")
    private String refundOrderInterfaceName;

    @Value("${xm.orderQueryUrl}")
    private String orderQueryUrl;

    @Value("${xm.orderQueryInterfaceName}")
    private String orderQueryInterfaceName;


    @Value("${xm.version}")
    private String version;

    /////////////////////西煤支付///////////////
    /** 西煤接口
     * @Description
     * @Author lchl
     * @Date 2019/6/5 4:53 PM
     * @param order
     * @return java.lang.String
     */
    public String tradeWapPay(Order order) {
        String result = "";


        // 接口请求地址
        String custId = merchantId + order.getUserId();

        //交易数据
        String tranData = "";
        //币种
        String curType = "CNY";
        // 商户接收支付成功数据的地址
        String returnURL = PayConfig.getH5DomainUrl().concat(returnUrl+order.getOrderId());
        log.debug("--returnURL--"+returnURL);
        // 商户接收支付成功消息的地址（后台返回）
        String notifyURL = PayConfig.getDomainUrl().concat(notifyUrl);

        // 交易数据
        tranData = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                "<orderRequest>" +
                // 二级商户号, 商户在平台申请的商户号(当前实际发生交易的商户)
                "<merchantId>" + merchantId + "</merchantId>" +
                // 商户客户唯一标识,由商户号及该客户在商户的唯一标识拼接而成。如：M100000022100000000
                "<custId>" + custId + "</custId>" +
                // 币种, CNY
                "<curType>" + curType + "</curType>" +
                // 前台返回URL
                "<returnURL>" + returnURL + "</returnURL>" +
                // 后台通知URL
                "<notifyURL>" + notifyURL + "</notifyURL>" +
                // 商户订单号
                "<orderNo>" + order.getOutTradeNo() + "</orderNo>" +
                // 支付金额
                "<orderAmt>" + order.getTradeFee() + "</orderAmt>" +
                // 商品名称
                "<goodsName>" + order.getAppName()+ "</goodsName>" +
                // 商品编号
                "<goodsNo>" + order.getAppId() + "</goodsNo>" +
                // 交易类型： 1.实物交易；2.虚拟交易
                "<transactionType>" + 2 + "</transactionType>" +
                // 备注
                "<remark>remark</remark>" +
                "</orderRequest>";


        String merSignMsg = new String(ProcessMessage.signMessage(tranData, strRealPath, keyPassword));
        String tranDataBase64 = ProcessMessage.Base64Encode(tranData.getBytes());

        log.debug("-tradeWapPay-tranData:" + tranDataBase64);
        log.debug("-tradeWapPay-merSignMsg:" + merSignMsg);

        result = wapUrl + "?"+"&orderType=10"+"&version="+ version+"&merSignMsg="+merSignMsg.replaceAll("=","lchlst")+"&transType=2"+"&&FAPView=JSON"+"&merchantId="+merchantId+"&tranData="+tranDataBase64.replaceAll("=","lchlst")+"&interfaceName=checkMerOrder";
        return result;
    }

    /** 西煤支付退款接口
     * @Description
     * @Author lchl
     * @Date 2019/6/5 5:21 PM
     * @param order
     * @return java.lang.String
     */
    public String doRefundRequest(Order order){
        HttpClient httpClient = new HttpClient();

        String orderNo = order.getOutTradeNo();
        String refundAmt = order.getTradeFee().toString();
        String notifyURL = PayConfig.getDomainUrl().concat(refundNotifyUrl);;

        String tranData = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                "<orderRequest>" +
                "<merchantId>" + merchantId + "</merchantId>" +			// 商户号
                "<orderNo>" + orderNo + "</orderNo>" +					// 原商户订单编号
                "<refundAmt>" + refundAmt + "</refundAmt>" +				// 退款金额
                "<notifyURL>" + notifyURL + "</notifyURL>" +				// 退款结果通知URL
                "</orderRequest>";

        String merSignMsg  = new String(ProcessMessage.signMessage(tranData, strRealPath, keyPassword));
        String tranDataBase64 = ProcessMessage.Base64Encode(tranData.getBytes());

        log.debug(merSignMsg);
        log.debug(tranDataBase64);

        PostMethod mypost = new PostMethod(refundUrl);

        mypost.addParameter("interfaceName", refundOrderInterfaceName);	// 接口名称 refundOrder
        mypost.addParameter("version", version);	// 版本号,使用值: WAP1.0
        mypost.addParameter("tranData", tranDataBase64);			// 交易数据密文串
        mypost.addParameter("merSignMsg", merSignMsg);			// 签名串
        mypost.addParameter("merchantId", merchantId);			// 商户号

        try {
            int re_code = httpClient.executeMethod(mypost);

            if (re_code == 200) {
                log.debug("-----------------退款接口返回数据--BEGIN--------------------");
                log.debug("-----返回结果------"+mypost.getResponseBodyAsString());
                return mypost.getResponseBodyAsString();

            }else{
                log.error("-XimeiPayUtil-doRefundRequest-退款失败--order--"+JSON.toJSONString(order));
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("-XimeiPayUtil-doRefundRequest-退款失败-IOException-order--"+JSON.toJSONString(order));
        }
        return null;
    }

    /** 查询订单
     * @Description
     * @Author lchl
     * @Date 2019/6/5 3:06 PM
     * @param order
     * @param refund true 查询退款订单
     * @return java.lang.String
     */
    public String doQueryRequest(Order order,boolean refund) {

        HttpClient httpClient = new HttpClient();

        String orderNo = order.getOutTradeNo();
        String code = order.getCode();
        String tranData = "";
        if(refund){
            tranData = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                    "<orderRequest>" +
                    "<merchantId>" + merchantId + "</merchantId>" +
                    "<orderNo>" + orderNo + "</orderNo>" +
                    "<tranSerialNo>" + code + "</tranSerialNo>" +
                    "</orderRequest>";
        }else {
            tranData = "<?xml version=\"1.0\" encoding=\"GBK\"?>" +
                    "<orderRequest>" +
                    "<merchantId>" + merchantId + "</merchantId>" +
                    "<orderNo>" + orderNo + "</orderNo>" +
                    "</orderRequest>";
        }

        String merSignMsg  = new String(ProcessMessage.signMessage(tranData, strRealPath, keyPassword));
        String tranDataBase64 = ProcessMessage.Base64Encode(tranData.getBytes());

        log.debug(merSignMsg);
        log.debug(tranDataBase64);

        PostMethod mypost = new PostMethod(orderQueryUrl);

        mypost.addParameter("interfaceName",orderQueryInterfaceName);	// 接口名称 queryOrder
        mypost.addParameter("version", version);	// 版本号,使用值: WAP1.0
        mypost.addParameter("tranData", tranDataBase64);			// 交易数据密文串
        mypost.addParameter("merSignMsg", merSignMsg);			// 签名串
        mypost.addParameter("merchantId", merchantId);			// 商户号

        try {
            int re_code = httpClient.executeMethod(mypost);

            if (re_code == 200) {
                log.debug("-----------------查证接口返回数据--BEGIN--------------------");
                return mypost.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





    ///////////////////////////////////////////////
    @Override
    public String refundTest(BigDecimal tradeFee, String orderNo) {
        Order order = new Order();
        order.setOutTradeNo(orderNo);
        order.setTradeFee(tradeFee);
        String result = doRefundRequest(order);

        if(null!=result && !"".equals(result)){
            Map<String, String> responseMap = (Map<String, String>) JSON.parse(new String(ProcessMessage.Base64Decode(result)));
            log.debug("--XimeiPayServiceImpl-refundTest-result-"+JSON.toJSONString(responseMap));

            String returnCode = responseMap.get("returnCode");
            result = JSON.toJSONString(responseMap);
            if(null!=returnCode && !"".equals(returnCode)){
                String returnMessage = responseMap.get("returnMessage");
                log.info("-refundTest--退款错误-returnCode--"+returnCode+"---returnMessage---"+returnMessage);
            }else{
                String tranDataXml = new String(ProcessMessage.Base64Decode(responseMap.get("tranData")));

                try {
                    Map<String,String> resultMap = WXPayUtil.xmlToMap(tranDataXml);
                    log.debug("-refundTest-XimeiPayServiceImpl-refundTest-resultMap-"+JSON.toJSONString(resultMap));
                    result = JSON.toJSONString(resultMap);
                    String outTradeNo = resultMap.get("orderNo");
                    String tradeNo = resultMap.get("tranSerialNo");
                    String orderStatus = resultMap.get("tranStat");
                    boolean orderStatusB = "31".equals(orderStatus);

                    tradeRecordService.insert(new TradeRecord(CommUtils.lowerUUID(),tradeNo,outTradeNo, new Date(), refundUrl,
                            JSON.toJSONString(order), orderStatusB?4:1,1, new Date(), JSON.toJSONString(resultMap),2));
                    if(orderStatusB){
                        order.setTradeStatus(Order.TradeStatusConst.REFUND_SICCESS);
                    }else{
                        order.setTradeStatus(Order.TradeStatusConst.WAIT_REFUND);
                        order.setStatus(6);//待退款
                        order.setSupStatus(3);//sup 充值失败
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("--XimeiPayServiceImpl-orderBack--xmlToMap-"+e);
                }
            }

        }else{
            log.error("西煤支付退款错误");
        }

        return result;
    }

    /** 查询退款订单
     * @Description
     * @Author lchl
     * @Date 2019/6/5 3:37 PM
     * @param orderNo
     * @param tranSerialNo
     * @return java.lang.String
     */
    @Override
    public String queryOrder(String orderNo, String tranSerialNo) {

        String resultStr = "";
        Order order = new Order();
        order.setOutTradeNo(orderNo);
        String responseStr = "";
        if(null!=tranSerialNo && !"".equals(tranSerialNo)){
            order.setCode(tranSerialNo);
            responseStr = doQueryRequest(order,true);
        }else{
            responseStr = doQueryRequest(order,false);
        }

        log.debug("--XimeiPayServiceImpl--queryOrder--responseStr--"+responseStr);

        if(null!=responseStr && !"".equals(responseStr)){

            Map<String, String> responseMap = (Map<String, String>) JSON.parse(ProcessMessage.Base64Decode(responseStr));

            log.debug("--XimeiPayServiceImpl-queryOrder-result-"+JSON.toJSONString(responseMap));
            resultStr = JSON.toJSONString(responseMap);
            String returnCode = responseMap.get("returnCode");
            String returnMessage = responseMap.get("returnMessage");
            if((null!=returnCode && !"".equals(returnCode)) || (null!=returnMessage && !"".equals(returnMessage))){
                log.info("--returnCode--"+returnCode+"---returnMessage---"+returnMessage);
            }else{
                String tranDataXml = new String(ProcessMessage.Base64Decode(responseMap.get("tranData")));
                resultStr = tranDataXml;
            }
        }else{
            log.error("西煤支付查询错误");
        }

        return resultStr;
    }

}
