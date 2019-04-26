package com.jzy.api.service.biz.impl;

import com.jzy.api.constant.SupConfig;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.SupService;
import com.jzy.api.util.MyEncrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SupServiceImpl implements SupService {

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
//                .append("&orderIp=").append("")
                // .append("&noticeUrl=").append(iConfigService.valueDomainUrl(SupConfig.SUP_RECEIVE_CALLBACK_URL))
                .append("&sign=").append(sign);
        // logger.debug("sup充值提交请求:{}", SupConfig.SUP_ORDER_RECEIVE.concat(urlData.toString()));
        return SupConfig.SUP_ORDER_RECEIVE.concat(urlData.toString());
    }
}
