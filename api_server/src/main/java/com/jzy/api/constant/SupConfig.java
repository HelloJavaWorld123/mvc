package com.jzy.api.constant;

import com.alibaba.fastjson.JSONObject;
import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.util.*;

import java.util.Map;

public class SupConfig {

    // private static IDealerService iDealerService = (IDealerService) CustomerContextAware.getBean(IDealerService.class);

//-----------------------------SUPAPI正式接口地址-----------------------------------------------------------
    /** SUPAPI商品列表 */
    public final static String SUPAPI_GETLIST = "http://wx.koudl.cn/api/Interface/GetList?";
    /** SUPAPI商品详情 */
    public final static String SUPAPI_GETPRODUCTINFO = "http://wx.koudl.cn/api/Interface/GetProductInfo?";
    /** SUPAPI商品价格 */
    public final static String SUPAPI_GETPRODUCPRICE = "http://wx.koudl.cn/api/Interface/GetProductPrice?";

    //-----------------------------SUP正式/测试接口地址-----------------------------------------------------------
    /**
     * SUP订单提交
     */
    public final static String SUP_ORDER_RECEIVE = "http://supi2.900sup.cn/Service/OrderReceive.ashx?";
    /**
     * SUP订单查询
     */
    public final static String SUP_ORDER_QRY = "http://supq2.900sup.cn/Service/CommOrderQry.ashx?";

    /** sup异步通知config_id */
    public final static String SUP_RECEIVE_CALLBACK_URL = "sup_receive_callback_url";

    //---------------------------SUP同步/异步状态----------------------------------------------------------
    /** 状态 */
    public final static String SUP_STATUS_01 = "01";
    /** 失败 */
    public final static String SUP_STATUS_02 = "02";
    /** 重复 */
    public final static String SUP_STATUS_03 = "03";

    /** 商户号 */
    public final static String SUPAPI_BUSINESSID = "Num10394";

    /** app */
    public final static String SUPAPI_APIID = "1";

    /** key */
    public final static String SUPAPI_KEY = "5e4054c5eb9847f29409fdf2253e5b48";

    /** 3des */
    public final static String SUPAPI_DES = "d11f85b3bf5c42ec9b760b5908395ea0";

    /**
     * SUPAPI请求
     * @param url     请求地址
     * @param urlData 请求地址后追加的参数 k1=v1&k2=v2……
     * @param postMap 请求的数据,最后转换为json格式字符串
     * @param dealerId 经销商id
     * @return
     */
    public static String common(String url, String urlData, Map<String, Object> postMap , String dealerId) {
        Dealer dealer = new Dealer(); // iDealerService.queryInfo(Integer.valueOf(dealerId));
        String apiBusinessId = dealer.getSupBusinessid();
        String apiDes = dealer.getPubkey();
        String apiKey = dealer.getPrikey();

        // 时间戳
        String timeStamp = DateUtils.getIntTimeStamp(null);
        // md5明文
        String md5Data = apiBusinessId + SupConfig.SUPAPI_APIID + timeStamp + apiKey;
        // md5密文
        String sign = MyEncrypt.getInstance().md5(md5Data);
        // des3明文
        String desData = "Timestamp=" + timeStamp + "&" + "ApiId=" + SupConfig.SUPAPI_APIID + "&" + "Sign=" + sign;
        //des3密文
        String des = DesUtil.des3Eencrypt(desData, apiDes);
        // des3编码
        des = MyStringUtil.getURLEncoderString(des);

        StringBuilder urlBuilder = new StringBuilder(url);
        // 拼接url数据
        urlBuilder.append("BusinessID=").append(apiBusinessId).append("&Data=").append(des);
        if (urlData != null) {
            urlBuilder.append("&").append(urlData);
        }
        String postData = "";
        // 判断postMap是否为空,不为空转换为
        if (postMap != null) {
            JSONObject json = new JSONObject(postMap);
            postData = json.toJSONString();
        }
        return MyHttp.sendPost(urlBuilder.toString(), postData);
    }

}
