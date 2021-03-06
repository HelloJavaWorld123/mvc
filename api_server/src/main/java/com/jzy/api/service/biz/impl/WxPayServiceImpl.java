package com.jzy.api.service.biz.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jzy.api.constant.WXPayConstants;
import com.jzy.api.constant.WechatConstant;
import com.jzy.api.model.biz.Order;
import com.jzy.api.model.biz.SecurityToken;
import com.jzy.api.model.biz.TradeRecord;
import com.jzy.api.model.sys.UserAuth;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.biz.SupService;
import com.jzy.api.service.biz.TradeRecordService;
import com.jzy.api.service.biz.WxPayService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.UserAuthService;
import com.jzy.api.service.wx.WXPay;
import com.jzy.api.service.wx.WXPayConfig;
import com.jzy.api.service.wx.WXPayUtil;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.DateUtils;
import com.jzy.api.util.MD5Util;
import com.jzy.api.util.MyHttp;
import com.jzy.framework.cache.EmpCache;
import com.jzy.framework.cache.UserCache;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.exception.PayException;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.impl.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.jzy.api.constant.WXPayConstants.*;
import static com.jzy.api.constant.WechatConstant.*;

/**
 * <b>功能：</b>支付宝支付业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190419&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Service
public class WxPayServiceImpl extends GenericServiceImpl implements WxPayService {

    private static final String SYSTEM_OPERATION = "充值失败";

    @Value("${basic_site_dns}")
    private String domainUrl;

    @Value("${h5_sit_dns}")
    private String h5DomainUrl;

    /**
     * 微信appId
     */
    @Value("${wx_app_id}")
    private String appId;
    /**
     * e
     * 微信授权回调
     */
    @Value("${wx_auth_callback_url}")
    private String callbackUrl;
    /**
     * 回调地址
     */
    @Value("${wx_app_secret}")
    private String appSecret;

    @Resource
    private OrderService orderService;

    @Resource
    private TradeRecordService tradeRecordService;

    @Resource
    private SupService supService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private TableKeyService tableKeyService;

    @Override
    public String getUrlByTypeAndyToken(String oauthType, String tokenHeader) {
        String authorizeUrl = null;
        switch (oauthType) {
            case "baseoauth":
                authorizeUrl = authorize_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(tokenHeader))
                        .replace("SCOPE", SCOPE_SNSAPI_BASE)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
            case "oauth":
                authorizeUrl = authorize_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(domainUrl.concat(callbackUrl)))
                        .replace("SCOPE", SCOPE_SNSAPI_BASE)
                        .replace("STATE", tokenHeader);
                break;
            case "qroauth":
                authorizeUrl = website_oauth_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(domainUrl.concat(callbackUrl)))
                        .replace("SCOPE", SCOPE_SNSAPI_LOGIN)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
        }
        return authorizeUrl;
    }

    /**
     * <b>功能描述：</b>支付<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public ApiResult pay(HttpServletRequest request, Order order) {
        Map<String, String> data = new HashMap<>();
        // 统一下单
        data.put("body", WXPayConstants.BODY + "-" + order.getAppName());
        data.put("out_trade_no", order.getOutTradeNo());
        // 金额转换
        data.put("total_fee", WXPayUtil.getMoney(order.getTradeFee().toString()));
        data.put("spbill_create_ip", MyHttp.getIpAddr(request));
        Date date = new Date();
        data.put("time_start", DateUtils.date2TimeStr(date));
        // 订单失效时间为15分钟之后
        data.put("time_expire", DateUtils.date2TimeStr(new Date(date.getTime() + 15 * 60 * 1000)));
        UserAuth userAuth = userAuthService.queryUserAuthByUserId(getUserId(),getFrontDealerId());
        data.put("trade_type", WXPayConstants.TradeType.MWEB.toString());
        // 从微信公众号中进入支付，后面修改，使用前端环境传值来判断
//        boolean isUserAuth = (userAuth != null && userAuth.getIsWxAuth() == 1);
        boolean isUserAuth = (userAuth != null && order.getIsWxAuth() == 1);
        if (isUserAuth) {
            log.debug("from wx openId pay：" + userAuth.getUserId());
            data.put("openid", StringUtils.isEmpty(userAuth.getUserId()) ? "ogasLxN9l-FeCs0dIKzixTw9KYo0" : userAuth.getUserId());
            data.put("trade_type", WXPayConstants.TradeType.JSAPI.toString());
        }
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("outTradeNo",order.getOutTradeNo());
        paramsMap.put("trusteeship",0);

        String tradeRecordId = tradeRecordService.queryIdByParams(paramsMap);
        // 返回支付标识并加签
        Map<String, String> responseData;
        try {
            responseData = unifiedOrderwx(data,tradeRecordId);
        } catch (ParseException e) {
            log.debug("微信返回参数解析异常", e);
            throw new PayException("微信返回参数解析异常");
        }

        log.debug("：：：prepay_id : " + responseData.get("prepay_id"));

        Map<String, String> payMap = new HashMap<>();
        payMap.put("appId", WXPayConfig.getInstance().getAppID());
        payMap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
        payMap.put("nonceStr", WXPayUtil.generateNonceStr());
        payMap.put("package", "prepay_id=".concat(StringUtils.isEmpty(responseData.get("prepay_id")) ? "" : responseData.get("prepay_id")));
        payMap.put("signType", MD5);
        String paySign;
        try {
            paySign = WXPayUtil.generateSignature(payMap);
        } catch (Exception e) {
            log.error("微信签名异常！", e);
            throw new PayException("微信签名异常！");
        }
        payMap.put("paySign", paySign);
        payMap.put("mweb_url", StringUtils.isEmpty(responseData.get("mweb_url")) ? "" : responseData.get("mweb_url").concat("&redirect_url=").concat(getURLEncoderString(h5DomainUrl.concat("/result?orderId=".concat(order.getOrderId())))));
        payMap.put("tradeMethod", "0");
        payMap.put("orderId", order.getOrderId());
        if (WXPayConstants.FAIL.equals(responseData.get("result_code"))) {
            //order.setStatus(3);
            return new ApiResult<>().fail(responseData.get("err_code_des"));
        }
        if (isUserAuth) {
            return new ApiResult<>().success(JSONObject.toJSONString(payMap));
        }
        //未授权情况
        Map<String, String> otherMap = new HashMap<>();
        otherMap.put("url", payMap.get("mweb_url"));
        otherMap.put("orderId", order.getOrderId());
        return new ApiResult<>().success(JSONObject.toJSONString(otherMap));
    }

    private Map<String, String> unifiedOrderwx(Map<String, String> params, String tradeRecordId) throws ParseException {
        Map<String, String> map;
        if(null!=tradeRecordId && !"".equals(tradeRecordId)){
            params.put("attach", tradeRecordId);
        }else{
            params.put("attach", CommUtils.lowerUUID());
        }

        WXPay wxpay;
        try {
            wxpay = new WXPay(WXPayConfig.getInstance());
            map = wxpay.unifiedOrder(params);
        } catch (Exception e) {
            log.error("：：：Err - Wechat Pay 预付款.", e);
            throw new PayException("微信服务不可用");
        }
        boolean resultStatus = SUCCESS.equalsIgnoreCase(map.get("result_code"));
        log.debug("：：：wechat unified order result.".concat(map.toString())); // 平台记录交易
        String reqUrl = wxpay.isUseSandbox() ? SANDBOX_UNIFIEDORDER_URL_SUFFIX : UNIFIEDORDER_URL_SUFFIX;

        if(null!=tradeRecordId && !"".equals(tradeRecordId)){
        }else{
            tradeRecordService.insert(new TradeRecord(params.get("attach"), params.get("out_trade_no"), DateUtils.timeStr2Date(params.get("time_start")), reqUrl, params.toString(), resultStatus ? 1 : 2, 0, new Date(), map.toString(), 0));
        }
        return map;
    }

    /**
     * <b>功能描述：</b>获取订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int queryOrderStatus(Order order) {
        Map<String, String> queryMap = queryOrderwx(order.getOutTradeNo());
        log.debug("：：：Wechat Notify Request." + queryMap.toString());
        try {
            WXPay wxpay = new WXPay(WXPayConfig.getInstance(), true);
            if (wxpay.isPayResultNotifySignatureValid(queryMap)) {
                log.debug("：：：Wechat Notify Sign Verify Success.".concat(queryMap.toString()));
                // 业务处理
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                String wxTradeState = queryMap.get("trade_state");
                log.info("wechat webapp_result,orderId=".concat(order.getOrderId()).concat(",out_trade_no=").concat(order.getOutTradeNo()).concat(",wechat支付状态:") + wxTradeState);
//                if (TradeState.SUCCESS.toString().equals(wxTradeState)) {
//                    return 2;
//                }
                if (TradeState.NOTPAY.toString().equals(wxTradeState)) {
                    return 0;
                }
                return 1;
//                if (TradeState.REFUND.toString().equals(wxTradeState) || TradeState.PAYERROR.toString().equals(wxTradeState)) {
//                    return 3;
//                }
//                if (TradeState.CLOSED.toString().equals(wxTradeState)){
//                    return 4;
//                }
            } else { // 签名错误，如果数据里没有sign字段，也认为是签名错误
                log.info("：：：Wechat Notify Sign Verify Failed. " + queryMap.toString());
            }
        } catch (Exception e) {
            log.error("：：：Err - 微信支付回调异常.", e);
        }
        return 0;
    }

    /**
     * <b>功能描述：</b>微信支付回调<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param notifyMap 微信支付返回参数
     */
    @Override
    public String updateWxCallBack(Map<String, String> notifyMap) throws Exception {
        String returnCode = FAIL;
        WXPay wxpay = new WXPay(WXPayConfig.getInstance(), true);
        // 加密信息，仅申请退款结果通知时有该参数
        String reqInfo = notifyMap.get("req_info");
        if (StringUtils.isEmpty(reqInfo)) {
            // 签名正确
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                String outTradeNo = notifyMap.get("out_trade_no");
                String transactionId = notifyMap.get("transaction_id");
                String totalFee = notifyMap.get("total_fee");
                BigDecimal tradeFee = new BigDecimal(WXPayUtil.changeF2Y(totalFee));
                //String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
                String orderId = orderService.queryOrderIdByoutTradeNo(outTradeNo);

                log.debug("-支付回调-updateWxCallBack--orderId---"+orderId+"-outTradeNo---"+outTradeNo+"----transactionId---"+transactionId+"---result_code---"+notifyMap.get("result_code"));

                if(null==orderId || "".equals(orderId) ){
                    log.error("--重大error---没有查到此订单---"+outTradeNo+"--参数--"+ notifyMap.toString());
                    return returnXML(SUCCESS);
                }

                tradeRecordService.updateWxCallbackStatus(transactionId, notifyMap.get("result_code").equalsIgnoreCase(SUCCESS) ? 4 : 3, notifyMap.toString(), notifyMap.get("attach"), 1);
                // 业务处理
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                if (notifyMap.get("result_code").equalsIgnoreCase(SUCCESS)) {
                    supService.commitOrderToSup(orderId, transactionId, tradeFee);
                } else {
                    //微信退款
                    orderService.tradeRefund(orderService.queryOrderById(orderId));
                }
                returnCode = SUCCESS;
                log.debug("：：：Wechat Notify Sign Verify Success.".concat(notifyMap.toString()));
            } else { // 签名错误，如果数据里没有sign字段，也认为是签名错误
                log.debug("：：：Wechat Notify Sign Verify Failed. " + notifyMap.toString());
                throw new PayException(notifyMap.toString());
            }
        } else {
            // 解密req_info
            String decryptReqInfo = WXPayUtil.decrypt(reqInfo);
            Map<String, String> reqInfoMap = WXPayUtil.xmlToMap(decryptReqInfo);
            log.debug("：：：Wechat Refund Notify Result. ".concat(decryptReqInfo));
            boolean refundStatus = reqInfoMap.get("refund_status").equalsIgnoreCase(SUCCESS);
            String outRefundNo = reqInfoMap.get("out_refund_no");
            String outTradeNo = reqInfoMap.get("out_trade_no");

            //String orderId = outTradeNo.substring(0, outTradeNo.length() - 7);
            String orderId = orderService.queryOrderIdByoutTradeNo(outTradeNo);
            log.debug("-退款回调-updateWxCallBack--orderId---"+orderId+"-outTradeNo---"+outTradeNo+"---outRefundNo---"+outRefundNo+"---refundStatus---"+refundStatus);
            tradeRecordService.updateBgRespByOperatorStatus(reqInfoMap.get("refund_id"), refundStatus ? 4 : 3, reqInfoMap.toString(), outRefundNo, 1);
            if (refundStatus) {
                orderService.updateTradeStatus(orderId, Order.TradeStatusConst.REFUND_SICCESS);
                returnCode = SUCCESS;
            } else {
                // 重新提交退款申请(一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号)
                refundOrderwx(reqInfoMap.get("out_trade_no"), outRefundNo, new BigDecimal(reqInfoMap.get("total_fee")), new BigDecimal(reqInfoMap.get("refund_fee")));
                log.debug("：：：微信退款失败，重新申请退款.退款单号:" + outRefundNo);
            }
        }
        return returnXML(returnCode);
    }

    /**
     * <b>功能描述：</b>根据code获取token<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public SecurityToken updateSecurityToken(String code, String state) {
        if (StringUtils.isEmpty(code)) {
            return getAccessTokenAndState(state);
        }
        return getOAuthToken(code, state);
    }

    private SecurityToken getAccessTokenAndState(String state) {
        log.debug("----getAccessTokenAndState---token[state]---"+state);
        SecurityToken accessToken = null;
        String requestUrl = WechatConstant.token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
        JSONObject result = null;
        try {
            result = JSONObject.parseObject(MyHttp.sendGet(requestUrl, null));
        } catch (Exception e) {
            log.error("：：：Wechat gets access_token exception", e);
        }
        if (!StringUtils.isEmpty(result)) {
            if (StringUtils.isEmpty(result.get(WechatConstant.ERRCODE))) {
                accessToken = new SecurityToken(CommUtils.upperUUID(), result.getString(WechatConstant.ACCESS_TOKEN),
                        result.getInteger(WechatConstant.EXPIRES_IN), 0, new Date());
                String unionId = "";//getUnionId(accessToken);
                log.debug("----getAccessTokenAndState---openId---"+result.getString(WechatConstant.OPENID)+"---unionId---"+unionId);
                insertUserCacheAndDB(state, result.getString(WechatConstant.OPENID),unionId);
            } else {
                log.error("：：：Wechat failed to get access_token - errcode:" + result.getString(WechatConstant.ERRCODE) + ", errmsg:"
                        + result.getString(WechatConstant.ERRMSG));
                log.debug("--getOAuthToken-result-ERRCODE-清除clearCacheAndDB---"+state);
                clearCacheAndDB(state);
            }
        }else{
            log.debug("--getOAuthToken-result-empty-清除clearCacheAndDB---"+state);
            clearCacheAndDB(state);
        }
        return accessToken;
    }

    /**
     * <b>功能描述：</b>通过code换取网页授权access_token<br>
     * <b>修订记录：</b><br>
     * <li>20190423&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private SecurityToken getOAuthToken(String code, String token) {
        SecurityToken oAuthToken = null;
        String requestUrl = WechatConstant.oauth_url.replace("APPID", appId).replace("SECRET", appSecret)
                .replace("CODE", code);
        log.debug("---getOAuthToken--token---"+token+"oauthurl:" + requestUrl);
        JSONObject result = JSONObject.parseObject(MyHttp.sendGet(requestUrl, null));
        if (!StringUtils.isEmpty(result)) {
            if (null == result.getString(WechatConstant.ERRCODE)) {
                oAuthToken = new SecurityToken(CommUtils.upperUUID(), result.getString(WechatConstant.ACCESS_TOKEN),
                        result.getInteger(WechatConstant.EXPIRES_IN), result.getString(WechatConstant.REFRESH_TOKEN),
                        result.getString(WechatConstant.OPENID), result.getString(WechatConstant.SCOPE), 1, new Date());
                // iRedisService.setValue(OAUTH_WX_WEBSITE.concat(code), oAuthToken, result.getInteger(WechatConstant.EXPIRES_IN));

                String unionId = "";//getUnionId(oAuthToken);
                log.debug("----getOAuthToken---openId---"+result.getString(WechatConstant.OPENID)+"---unionId---"+unionId);
                insertUserCacheAndDB(token, result.getString(WechatConstant.OPENID),unionId);
            } else {
                log.error("：：：Wechat website failed to get oauth_token - errcode:" + result.getString(WechatConstant.ERRCODE) + ", errmsg:"
                        + result.getString(WechatConstant.ERRMSG));
                log.debug("--getOAuthToken-result-ERRCODE-清除clearCacheAndDB---"+token);
                clearCacheAndDB(token);
            }
        }else{
            log.debug("--getOAuthToken-result-empty-清除clearCacheAndDB---"+token);
            clearCacheAndDB(token);
        }
        return oAuthToken;
    }
    private String getUnionId(SecurityToken oAuthToken){
        log.debug("---getUnionId---"+JSON.toJSONString(oAuthToken));
        String unionId = "";
        String getUinionIdUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+oAuthToken.getAccessToken()+"&openid="+oAuthToken.getOpenId()+"&lang=zh_CN";
        try {
            JSONObject result = JSONObject.parseObject(MyHttp.sendGet(getUinionIdUrl, null));

            if (!StringUtils.isEmpty(result)) {
                log.debug("---getUnionId--result-"+result.toString());
                unionId = result.getString("unionid");
            }
        } catch (Exception e) {
            log.error("：：：Wechat gets getUnionId exception", e);
        }
        return unionId;
    }

    @Resource
    private RedissonClient redissonClient;

    private void clearCacheAndDB(String token){
        RBucket<UserCache> userCacheRBucket = redissonClient.getBucket(token);
        UserCache userCache = userCacheRBucket.get();
        if (userCache == null) {
            throw new BusException("会员超时");
        }
        log.debug("--clearCacheAndDB-有数据-清除---"+token);
        redissonClient.getKeys().delete(token);
        throw new BusException("会员超时！！！");
    }

    /**
     * <b>功能描述：</b>重新缓存用户信息<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void insertUserCacheAndDB(String token,String openId,String unionId) {
        RBucket<UserCache> userCacheRBucket = redissonClient.getBucket(token);
        UserCache userCache = userCacheRBucket.get();
        if (userCache == null) {
            throw new BusException("会员超时");
        }
        userCache.setUserId(openId);
        userCacheRBucket.set(userCache, 1, TimeUnit.DAYS);

        UserAuth userAuthTemp = userAuthService.queryUserAuthByUserId(openId,userCache.getDealerId());
        if (null!=userAuthTemp){
            log.debug("-insertUserCacheAndDB-有数据---"+openId+"---dealerId---"+userCache.getDealerId());
        }else{

            // 存储用户信息到本地数据库中
            UserAuth userAuth = new UserAuth();
            userAuth.setId(tableKeyService.newKey("user_auth", "id", 1000));
            userAuth.setUserId(userCache.getUserId());
            userAuth.setIsWxAuth(1);
            userAuth.setDealerId(userCache.getDealerId());
            userAuth.setOpenId(unionId);

            userAuthService.insert(userAuth);
            log.debug("--insertUserCacheAndDB-没有数据-插入---"+token+"---"+userCache.getUserId()+"---dealerId----"+userCache.getDealerId());
        }


    }

    /**
     * <b>功能描述：</b>请求获取普通access_token<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private SecurityToken getAccessToken() {
        SecurityToken accessToken = null;
        String requestUrl = WechatConstant.token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
        JSONObject result = null;
        try {
            result = JSONObject.parseObject(MyHttp.sendGet(requestUrl, null));
        } catch (Exception e) {
            log.error("：：：Wechat gets access_token exception", e);
        }
        if (!StringUtils.isEmpty(result)) {
            if (StringUtils.isEmpty(result.get(WechatConstant.ERRCODE))) {
                accessToken = new SecurityToken(CommUtils.upperUUID(), result.getString(WechatConstant.ACCESS_TOKEN),
                        result.getInteger(WechatConstant.EXPIRES_IN), 0, new Date());
                // iRedisService.setValue(OAUTH_WX_UNIFIED, accessToken, accessToken.getExpiresIn());
                //insertUserCacheAndDB(token, result.getString(WechatConstant.OPENID));
            } else {
                log.error("：：：Wechat failed to get access_token - errcode:" + result.getString(WechatConstant.ERRCODE) + ", errmsg:"
                        + result.getString(WechatConstant.ERRMSG));
            }
        }
        return accessToken;
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
        Map<String, String> wxResult = refundOrderwx(order.getOutTradeNo(), order.getTradeFee());
        if (WXPayConstants.SUCCESS.equals(wxResult.get(WXPayConstants.RESULT_CODE))) {
            order.setTradeStatus(Order.TradeStatusConst.WAIT_REFUND);
            return true;
        }
        String errMgs = order.getOrderId() + "订单微信申请退款失败:" + wxResult.get(WXPayConstants.ERR_CODE) + "/" + wxResult.get(WXPayConstants.ERR_CODE_DES);
        log.error(errMgs);
        return false;
    }

    /** 废弃
     * <b>功能描述：</b>根据授权类型获取跳转url地址<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param oauthType "oauth":内置网页授权, "qroauth":网站应用授权
     */
    @Override
    public String getUrlByAuthType(String oauthType) {
        return getUrlByAuthType(oauthType, null);
    }

    /** 废弃
     * <b>功能描述：</b>根据授权类型获取跳转url地址<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param oauthType "oauth":内置网页授权, "qroauth":网站应用授权
     */
    @Override
    public String getUrlByAuthType(String oauthType, String uri) {
        String authorizeUrl = null;
        switch (oauthType) {
            case "baseoauth":
                authorizeUrl = authorize_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(uri))
                        .replace("SCOPE", SCOPE_SNSAPI_BASE)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
            case "oauth":
                authorizeUrl = authorize_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(domainUrl.concat(callbackUrl)))
                        .replace("SCOPE", SCOPE_SNSAPI_BASE)
                        .replace("STATE", getUserId());
                break;
            case "qroauth":
                authorizeUrl = website_oauth_url.replace("APPID", appId)
                        .replace("REDIRECT_URI", URLEncoder.encode(domainUrl.concat(callbackUrl)))
                        .replace("SCOPE", SCOPE_SNSAPI_LOGIN)
                        .replace("STATE", Base64.encodeBase64String("900Mall".getBytes()));
                break;
        }
        return authorizeUrl;
    }

    private Map<String, String> refundOrderwx(String out_trade_no, BigDecimal total_fee) {
        return this.refundOrderwx(out_trade_no, "", total_fee, total_fee);
    }

    private Map<String, String> refundOrderwx(String out_trade_no, String out_refund_no, BigDecimal total_fee, BigDecimal refund_fee) {
        return this.refundOrderwx(out_trade_no, out_refund_no, total_fee, refund_fee, null);
    }

    private Map<String, String> refundOrderwx(String out_trade_no, String out_refund_no, BigDecimal total_fee, BigDecimal refund_fee, String refund_desc) {
        Map<String, String> respmap = null, params = new HashMap<>();
        try {
            params.put("out_trade_no", out_trade_no);
            params.put("out_refund_no", StringUtils.isEmpty(out_refund_no) ? out_trade_no : out_refund_no);
            params.put("total_fee", total_fee.multiply(new BigDecimal(100)).intValue() + "");
            params.put("refund_fee", refund_fee.multiply(new BigDecimal(100)).intValue() + "");
            params.put("refund_desc", StringUtils.isEmpty(refund_desc) ? SYSTEM_OPERATION : refund_desc);
            WXPay wxpay = new WXPay(WXPayConfig.getInstance());
            respmap = wxpay.refund(params);
            boolean resultStatus = WXPayConstants.SUCCESS.equalsIgnoreCase(respmap.get("return_code"));

            log.debug("：：：wechat refund order result.".concat(respmap.toString()));

            respmap.put("trade_record_id", CommUtils.lowerUUID());

            String refundUrl = wxpay.isUseSandbox() ? SANDBOX_REFUND_URL_SUFFIX : REFUND_URL_SUFFIX;
            tradeRecordService.insert(new TradeRecord(respmap.get("trade_record_id"), params.get("out_refund_no"), new Date(), refundUrl, params.toString(), resultStatus ? 1 : 3, 1, new Date(), respmap.toString(), 0));
        } catch (Exception e) {
            log.error("：：：Err - Wechat Refund 申请退款.", e);

            throw new BusException("申请退款异常");
        }
        return respmap;
    }

    /**
     * <b>功能描述：</b>进行URL编码<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * wechat:返回给微信服务端的Xml
     *
     * @param returnCode [SUCCESS,FAIL]
     * @return
     */
    private String returnXML(String returnCode) {
        return "<xml><return_code><![CDATA[".concat(returnCode).concat("]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
    }

    public Map<String, String> queryOrderwx(String out_trade_no) {
        return this.queryOrderwx(out_trade_no, "");
    }

    public Map<String, String> queryOrderwx(String out_trade_no, String transaction_id) {
        Map<String, String> respmap = null, params = new HashMap<>();
        try {
            params.put("out_trade_no", out_trade_no);
            params.put("transaction_id", transaction_id);
            WXPay wxpay = new WXPay(WXPayConfig.getInstance());
            respmap = wxpay.orderQuery(params);
        } catch (Exception e) {
            log.error("：：：Err - Wechat Order Query 订单查询.", e);
            throw new BusException("微信服务不可用");
        }
        return respmap;
    }

    /**
     * 获取微信 JS-SDK 的配置
     * jiazk 2019年5月18日
     */
    @Override
    public Map<String, String> getSdkConfig(String url) {

        String ticket = get_ticket();
        if (ticket == "") {
            return null;
        }
        Map<String, String> configMap = sign(ticket, url);
        return configMap;
    }


    private static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId", WXPayConfig.getInstance().getAppID());
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private String get_ticket() {
        SecurityToken token = getAccessToken();
        String ticketUrl = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + token.getAccessToken();

        try {
            JSONObject result = JSONObject.parseObject(MyHttp.sendGet(ticketUrl, null));
            if (result.getString("ticket") != null) {
                return result.getString("ticket");
            }
        } catch (Exception e) {
            log.error("：：：Wechat gets ticket exception", e);
        }
        return "";
    }

    @Override
    protected GenericMapper getGenericMapper() {
        return null;
    }

    @Override
    protected EmpCache getDealer() {
        return super.getDealer();
    }
}
