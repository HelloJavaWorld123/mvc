package com.jzy.api.service.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jzy.api.constant.SupConfig;
import com.jzy.api.constant.WXPayConstants;
import com.jzy.api.dao.biz.SupRecordMapper;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.SupRecord;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.PayService;
import com.jzy.api.service.biz.SupService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyEncrypt;
import com.jzy.api.util.MyHttp;
import com.jzy.api.util.WXPayUtil;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class SupServiceImpl extends GenericServiceImpl<SupRecord> implements SupService {

    @Resource
    private SupRecordMapper supRecordMapper;

    @Resource
    private PaywayProvider paywayProvider;

    @Resource
    private OrderService orderService;

    @Override
    public void commitOrderToSup(Order order) throws Exception {
        String resultCode;
        String requestData = orderReceive(order);
        String resultXml = MyHttp.sendPost(requestData, null);
        log.debug("提交SUP订单同步返回,订单id:".concat(order.getOrderId()).concat("返回结果:").concat(resultXml));
        Map<String, String> resultMap = WXPayUtil.xmlToMap(resultXml);
        resultCode = resultMap.get("result");
        // sup同步返回成功
        if (SupConfig.SUP_STATUS_01.equals(resultCode)) {
            order.setSupStatus(1);
            orderService.update(order);
            // sup同步返回失败
        } else {
            tradeRefund(order);
        }
        // SUP充值记录
        SupRecord supRecord = supRecordMapper.querySupRecordByOrderId(order.getOrderId());
        if (Objects.isNull(supRecord)) {
            supRecordMapper.insert(new SupRecord(CommUtils.uniqueOrderStr(), order.getOrderId(), requestData, new Date(), 1, JSON.toJSONString(resultMap), ""));
        } else {
            supRecord.setReqAmount(supRecord.getReqAmount() + 1);
            supRecord.setReqTime(new Date());
            supRecord.setReqData(supRecord.getReqData() + ";" + requestData);
            supRecordMapper.updateWhereOrderId(supRecord);
        }
    }

    /**
     * <b>功能描述：</b>退单<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void tradeRefund(Order order) {
        PayService payService = paywayProvider.getPayService(order.getTradeMethod());
        payService.orderBack(order);
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

    @Override
    public String orderReceive(Order order) {
        // DealerMapper dealer = iDealerService.queryByUseridOrDefault(order.getUserId());
        String supBusinessId =  null; //dealer.getSupBusinessid();
        String supKey = null; // dealer.getSupKey();

        // AppInfoMapper ai  = iAppInfoService.queryAppById(order.getAiId());

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
                .append("&userName=").append(order.getAccount())
                .append("&gameName=").append(order.getAppName())
                .append("&gameAcct=").append(order.getGameAccount())
                .append("&gameArea=").append(gameArea)
                .append("&gameType=").append(order.getPriceTypeName())
                .append("&acctType=").append(order.getAcctType())
                .append("&goodsNum=").append(order.getSupPrice())
                // .append("&noticeUrl=").append(iConfigService.valueDomainUrl(SupConfig.SUP_RECEIVE_CALLBACK_URL))
                .append("&sign=").append(sign);
        log.debug("sup充值提交请求:{}", SupConfig.SUP_ORDER_RECEIVE.concat(urlData.toString()));
        return SupConfig.SUP_ORDER_RECEIVE.concat(urlData.toString());
    }

    @Override
    protected GenericMapper<SupRecord> getGenericMapper() {
        return supRecordMapper;
    }
}
