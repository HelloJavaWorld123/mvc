package com.jzy.api.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.jzy.api.constant.PayConfig;
import com.jzy.api.model.biz.Order;
import com.jzy.framework.exception.PayException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class AlipayUtil {

    private static final AlipayClient client;
    /**
     * 商户appid
     */
    public static final String APPID = "2018082861143792"; // 佳景支付
    /**
     * 私钥
     */
    public static final String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDR8kfL1rnluZmJ/d98itKbwe2Qg6luBcwKLpVZTA0VnRBnLmBODkGIn+Vpalz8uZm45WKevfBmTcvMrQWHb3QrIcnbdIYGxGpQgXb8QSbWwDDzTRVQNdVdxsMDvJ4r1BbWVt+PMV93JtHZgeYcQzBeBtoZWGbGZVRiVXaXHdaHRC/qf8+7i6GaV7hWrie9A055GTeQqZDG5W7K/vK6OlQ5R+1ibdEyRwxglU4CUrFQfT1EGNZFjwt6NHFrSbyjljO9p5qOQthoCOBHIKuBEtdSeKDdzlk+Wys1UnlJJQKT8hw7Pn6nI/PguYivOHoeC9jzkGtO5rqdhlL7qaW8Wh3/AgMBAAECggEBAJjvIJXAKVLKzKnUoYQbrCeCMQfgoP9gga25GVofKDjiXRyMj+LMR7i6oWcU7/+5Q92IWzq/qescKKVENI7gBYOV7XFLhIZRdhCG/qHq49vzzLSvJiNz06WoME78i0vqB2jDh77LfHtTb/I9yd40o3sbCq0D1HoZPksTbnvOZIPLOWoVKhNV0ibc9baZR3Zox6gXGHvQqq5/57JY1FdnWDu5FqoPgL3XjKEcNeBgVPJgG2Ux2EQLipi21qfSk+qOtLW/CgcuVpNWfzHKRZwlMvISbHQj7jEGx4hZJuFZj2t6s7uWAT9Bc5lyqMxl1ujQM484oJ8QtecieT1Z9wU6TGECgYEA+SHdgQulDKYMxZTnABQ0xI6MT1iJhN4SF2510+hwpDJ/zxexSwYYBaJWJdewPPEs9G1u9dQZdHGmdHt7M/rjzeslnkn9meRgD6XOW06olUUXrlkug7wn6TYGB+4a+Ec9ZgE4SuB0HcaR1EgnjudCooIXjOP08vfLVntTAfz35jUCgYEA17vhEVw6CLE0FHpbQLbP4QSeRzQbos+xvVIFxUQ5AzZ095oAkYMd9fHUCx7pS5UPed16VjZd8D2Xc13hC9ESftn5OXjBZ/IeYzxvxETNvunvzxzT/Jsejlc86uALmhd08lZfBS5WgVbuULBYTnfY3mri2gsvyQVfLi3phiFQqeMCgYEAoZYIgCmYXLLat9BTX6a7bv1yHgiz8VzD6TS48b/iR22FnkGkuZ9zNzxKmfLimTRoTwUnogR2miMYrpZkm5tP5ABvftJIzWbls2B8jpZfG6obqPapB8KjNEzY7o3OKliyhxWleFNKnQFtFn3SQorWmma8daJo/qof1bDZbwGvnD0CgYEAiQ23/5Jq4AjLwFK3VFaPnDGV6EBnajiuf0rzlY4w9iSLjJ2OzOLSjuQSRPpfVgAds9sLlGe3qB9I7ybuCNbK/EG4PlXD8Sh1O7AyhSr81tPB4P88gZd+gwlTDxhE5qEOT8KlXSJCHKtAfYx6nWcwJKUc907yDbBW9nx+hhL2LucCgYAZy8W5D3BYMiwCmOMvTAyvsW/UQ8rdVEA6429AkRDcrm7cVg4FdN/pIOfQokD/JIYx0lnicpKxQt99Q0TdThOE3b1FwLe9++oINaYUez7r2ptwRFOhX2w3aLzZzN7cGLM4kpzPpm4SZ2skIekBjFdeiNoYeUmtZ9CHIMDUlX7vaQ=="; // 佳景支付
    /**
     * 支付宝公钥
     */
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh+uAZzbBbcJ+lvM3PqaMVPXSjoxs6PLgjYgQ9wbC2RuKr/2DjgTu3O8OluvgGocUex5Xw1FzWoOZaYDwtOw8VQhzjdi5LdyQpwSSvu3qVnOlPqgP5rOd7g8HUinV4N2pEQpJUh20zVqQYdqUSRUfqkz7Z6w88k770+07gdSPCe4B0uXubeWCX14+QeRetWy3VS+sfKTtqSQiLe52L3ukayEgzLsvtJHQ40C0B8LVnLXfAZqYngi5JV5eSyOR8L0QDaCB9X1+xBmBe3cZBr5X84cFTVHqDqCBQIXftB9bYtOLY90Bs9uvnCOse1l6uRWfYZWnCJK6hrDPmewzg+4F+wIDAQAB"; // 佳景支付
    /**
     * 请求网关地址
     */
    public static final String URL = "https://openapi.alipay.com/gateway.do";
    /**
     * 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static final String notify_url = "/ali/payCallback";
    /**
     * 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
     */
    public static final String return_url = "/result?orderId=";
    /**
     * 编码
     */
    public static final String CHARSET = "UTF-8";
    /**
     * 返回格式
     */
    public static final String FORMAT = "json";
    /**
     * 日志记录目录
     */
    public static final String log_path = "/log";
    /**
     * RSA2
     */
    public static final String SIGNTYPE = "RSA2";

    static {
        client = new DefaultAlipayClient(URL, APPID, RSA_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGNTYPE);
    }

    /**
     * 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
     */
    public enum TradeState {
        WAIT_BUYER_PAY, TRADE_CLOSED, TRADE_SUCCESS,TRADE_FINISHED
    }

    /**
     * 返回参数整理
     *
     * @param parameterMap
     * @return
     */
    public static Map<String, String> respDataSort(Map<String, String[]> parameterMap) {
        Map<String, String> rstMap = new HashMap<>();
        Iterator<Map.Entry<String, String[]>> it = parameterMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String[]> entry = it.next();
            rstMap.put(entry.getKey(), entry.getValue()[0]);
        }
        return rstMap;
    }

    public static boolean signatureValid(Map<String, String> map) {
        try {
            return AlipaySignature.rsaCheckV1(map, ALIPAY_PUBLIC_KEY, CHARSET, SIGNTYPE);
        } catch (AlipayApiException e) {
//            logger.error("：：：支付宝验签异常.", e);
            return false;
        }
    }

    /**
     * <b>功能描述：</b>支付宝手机H5支付<br>
     * <b>修订记录：</b><br>
     * <li>20190501&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param outTradeNo 商户订单号，需要保证不重复
     * @param totalAmount 订单金额
     * @param subject 订单标题
     */
    public static String tradeWapPay(Order order, String subject) {
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        alipayRequest.setReturnUrl(PayConfig.getH5DomainUrl().concat(return_url + order.getOrderId()));
        alipayRequest.setNotifyUrl(PayConfig.getDomainUrl().concat(notify_url));
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + order.getOutTradeNo() + "\"," +
                " \"total_amount\":\"" + order.getTradeFee() + "\"," +
                " \"subject\":\"" + subject + "\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");
        String linkStr = "";
        try {
            AlipayTradeWapPayResponse alipayTradeWapPayResponse = client.pageExecute(alipayRequest, "GET");
            log.debug(JSON.toJSONString(alipayTradeWapPayResponse));
            linkStr = alipayTradeWapPayResponse.getBody();
            // 新增支付记录
        } catch (AlipayApiException e) {
            log.error("：：：支付宝手机支付请求异常.", e);
            throw new PayException("支付宝手机支付请求异常", e);
        }
        return linkStr;
    }

    /**
     * 退款申请
     * 参照接口：https://docs.open.alipay.com/api_1/alipay.trade.refund/
     *
     * @param out_trade_no   支付时传入的商户订单号，与trade_no必填一个
     * @param trade_no       支付时返回的支付宝交易号，与out_trade_no必填一个
     * @param refund_amount  本次退款金额(需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数)
     * @return 关键出参{refund_fee:该笔交易已退款的总金额}
     */
    public static AlipayTradeRefundResponse tradeRefund(String out_trade_no, String trade_no, BigDecimal refund_amount) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent("{" +
                " \"out_trade_no\":\"" + out_trade_no + "\"," +
                " \"trade_no\":\"" + trade_no + "\"," +
                " \"out_request_no\":\"\"," +
                " \"refund_amount\":\"" + refund_amount + "\"" +
                " }");
        AlipayTradeRefundResponse response = null;
        try {
            /**
             * {
             *   "buyerLogonId": "urx***@sandbox.com",
             *   "buyerUserId": "2088102173133335",
             *   "fundChange": "Y",
             *   "gmtRefundPay": "Jan 23, 2019 4:26:03 PM",
             *   "outTradeNo": "190123160621303237949345",
             *   "refundFee": "10.00",
             *   "sendBackFee": "0.00",
             *   "tradeNo": "2019012322001433330500664748",
             *   "code": "10000",
             *   "msg": "Success",
             *   "body": "{\"alipay_trade_refund_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"buyer_logon_id\":\"urx***@sandbox.com\",\"buyer_user_id\":\"2088102173133335\",\"fund_change\":\"Y\",\"gmt_refund_pay\":\"2019-01-23 16:26:03\",\"out_trade_no\":\"190123160621303237949345\",\"refund_fee\":\"10.00\",\"send_back_fee\":\"0.00\",\"trade_no\":\"2019012322001433330500664748\"},\"sign\":\"BNfwwarRedx6GBkeuGb6wHgUlTI+akamxppKtegFN9DUW87evNwlHPjIrRUSNYNO8Fi9UgH0ghKKfsU29giVRR/O1CUq0XvPKyZ0EbWFZnMYT0PkTa7q0DDBpqATmRFLMuY0/QHtVZ27p3r4w87Nlwhs+43+cbfI6cVLq43oOSFGO0p3mUAtSHHP4cieY4Nauuepo4tL26QhNp1bwoNxeml3VxsgC5DA6BblBDyjo47Wkzkg8LkIq80fu9i6c38L8ZqaknH7gAm+Fh5VjZzVPzrBv37s9BKkT1wQG8rA5gvtmj44lVyuMgK/4oGbUKYQ6aok+HWuzgOILKa2xhzPxw\u003d\u003d\"}",
             *   "params": {
             *     "biz_content": "{ \"out_trade_no\":\"190123160621303237949345\", \"trade_no\":\"\", \"out_request_no\":\"\", \"refund_amount\":\"10.0\" }"
             *   }
             * }
             */
            response = client.execute(request);
            boolean b =  new BigDecimal(response.getRefundFee()).equals(refund_amount);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 交易查询
     * 参照接口：https://docs.open.alipay.com/api_1/alipay.trade.query/
     *
     * @param out_trade_no 支付时传入的商户订单号
     * @return 关键出参{trade_no:支付宝28位交易号,out_trade_no:支付时传入的商户订单号,trade_status:交易当前状态}
     */
    public static AlipayTradeQueryResponse tradeQuery(String out_trade_no) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                " \"out_trade_no\":\"" + out_trade_no + "\"," +
                " \"trade_no\":\"\"" +
                " }");
        AlipayTradeQueryResponse response = null;
        try {
            /**
             * {
             *   "buyerLogonId": "urx***@sandbox.com",
             *   "buyerPayAmount": "0.00",
             *   "buyerUserId": "2088102173133335",
             *   "buyerUserType": "PRIVATE",
             *   "invoiceAmount": "0.00",
             *   "outTradeNo": "190123160621303237949345",
             *   "pointAmount": "0.00",
             *   "receiptAmount": "0.00",
             *   "sendPayDate": "Jan 23, 2019 4:07:04 PM",
             *   "totalAmount": "10.00",
             *   "tradeNo": "2019012322001433330500664748",
             *   "tradeStatus": "TRADE_SUCCESS",
             *   "code": "10000",
             *   "msg": "Success",
             *   "body": "{\"alipay_trade_query_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"buyer_logon_id\":\"urx***@sandbox.com\",\"buyer_pay_amount\":\"0.00\",\"buyer_user_id\":\"2088102173133335\",\"buyer_user_type\":\"PRIVATE\",\"invoice_amount\":\"0.00\",\"out_trade_no\":\"190123160621303237949345\",\"point_amount\":\"0.00\",\"receipt_amount\":\"0.00\",\"send_pay_date\":\"2019-01-23 16:07:04\",\"total_amount\":\"10.00\",\"trade_no\":\"2019012322001433330500664748\",\"trade_status\":\"TRADE_SUCCESS\"},\"sign\":\"Rng5ca19qOfioh8pw3Y1RaHJwHOR+Bl7p+FDuWYeQ2pX6WCV/gXeKNFPoty/A2LjjOyIPTBkXjl1tl9+GYE+ysF5TVW1ge7tE2tg6Guq3Q07p+AVAojANlQGol/yejyrNrE/QvlbASfKqI+D717uukZnQumhGD+aOOe1edCd+FZ4Jx7+k5ohE6wGp086YWnmg7zugM31Ao3YRaQS8hhBMxVGWdHQvzL+aN+VJnZR1uBqc68dKHNCgDmZ5Ucp+0alSuZx2jSDdTF3afATK3vQW7gKeMA4ppHZr91pVdXNWE9yzIShZDyO7TRIupAaCfpNATHLvaUGAxH/m8B3gg/pkw\u003d\u003d\"}",
             *   "params": {
             *     "biz_content": "{ \"out_trade_no\":\"190123160621303237949345\", \"trade_no\":\"\" }"
             *   }
             * }
             */
            response = client.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 退款查询
     *
     * @param trade_no 支付宝交易号，不能为空
     * @param out_trade_no 商户订单流水号
     * @return
     */
    public static AlipayTradeFastpayRefundQueryResponse tradeFastpayRefundQuery(String trade_no, String out_trade_no) {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizContent("{" +
                "\"trade_no\":\"" + trade_no + "\"," +
                "\"out_trade_no\":\"" + out_trade_no + "\"," +
                "\"out_request_no\":\"" + trade_no + "\"," +
                "\"org_pid\":\"\"" +
                "  }");
        AlipayTradeFastpayRefundQueryResponse response = null;
        try {
            /**
             * {
             *   "code": "10000",
             *   "msg": "Success",
             *   "body": "{\"alipay_trade_fastpay_refund_query_response\":{\"code\":\"10000\",\"msg\":\"Success\"},\"sign\":\"l6LBAIs589D+fclTsKVdIoofQS7y8spYM8CzT/ugXbumXmJsUOIFQWspjHRhjWjft+1DM46A5Di3mGymd5ivFsaXDBujUCHXMw18NBwojr91Cq861jbPp2Q5LScd5nTHvo8DgSGbTaw+mKjQrS0jZiUi5U6oa0SoQhQeQWCmS3VCX7rE6S4urdxK+bLGiiZNphy7a9hP5Ho8Xjtj0KdNZntaAjv8muW3htCm3YB38l4c4o4wOo3RZARRe2lvNI8DSmGiiqO5g0WBL/ALQ7HKn9Q9hqINxMKmA+/leWk2Sllto+jbitpUZ6fCnaymf3dzAb7Xd7DQkvot2+koE53Acg\u003d\u003d\"}",
             *   "params": {
             *     "biz_content": "{\"trade_no\":\"2019012322001433330500664748\",\"out_trade_no\":\"\",\"out_request_no\":\"2019012322001433330500664748\",\"org_pid\":\"\"  }"
             *   }
             * }
             */
            response = client.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

}
