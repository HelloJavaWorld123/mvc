package com.jzy.api.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class SignUtil {

    public final static String SECRET_KEY = "1308f109ecc2639e1246f31e999d74e7";
    /**
     * 加密密钥
     */
    private final static String APP_KEY = "MTMwOGYxMDllY2MyNjM5ZTEyNDZmMzFlOTk5ZDc0ZTc";
    /**
     * 字符编码
     */
    private final static String INPUT_CHARSET = "UTF-8";
    /**
     * 超时时间
     */
    private final static int TIME_OUT = 5 * 60;
    private final static String PARAM_SIGN = "Sign";
    private final static String PARAM_TIMESTAMP = "Timestamp";
    private final static Logger logger = Logger.getLogger(SignUtil.class);

    /**
     * 请求参数Map转换验证Map
     *
     * @param requestParams 请求参数Map
     * @param charset       是否要转utf8编码
     * @return
     */
    public static Map<String, String> toVerifyMap(Map<String, String[]> requestParams, boolean charset) {
        Map<String, String> params = new HashMap<>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            if (charset) valueStr = getContentString(valueStr, INPUT_CHARSET);
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<>();
        if (sArray == null || sArray.size() <= 0) return result;
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        return createLinkString(params, false);
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @param encode 是否需要UrlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode)
                value = urlEncode(value, INPUT_CHARSET);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 编码转换
     *
     * @param content
     * @param charset
     * @return byte[]
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 编码转换
     *
     * @param content
     * @param charset
     * @return String
     */
    private static String getContentString(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return new String(content.getBytes());
        }
        try {
            return new String(content.getBytes("ISO-8859-1"), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * URL转码
     *
     * @param content
     * @param charset
     * @return
     */
    private static String urlEncode(String content, String charset) {
        try {
            return URLEncoder.encode(content, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 生成要请求的签名参数数组
     *
     * @param sParaTemp 需要签名的参数Map
     * @return 要请求的签名参数数组
     */
    public static Map<String, String> signMap(Map<String, String[]> sParaTemp) {
        //请求参数Map转换验证Map，并生成要请求的签名参数数组
        return sign(toVerifyMap(sParaTemp, false));
    }

    /**
     * 生成要请求的签名参数数组
     *
     * @param sParaTemp 需要签名的参数
     * @return 要请求的签名参数数组
     */
    public static Map<String, String> sign(Map<String, String> sParaTemp) {
        return sign(sParaTemp, null);
    }

    /**
     * 生成要请求的签名参数数组
     *
     * @param sParaTemp 需要签名的参数
     * @return 要请求的签名参数数组
     */
    public static Map<String, String> sign(Map<String, String> sParaTemp, String key) {
        sParaTemp.put(PARAM_TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        Map<String, String> sPara = paraFilter(sParaTemp);
        //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String prestr = createLinkString(sPara);
        byte[] contentBytes = getContentBytes(prestr.concat((StringUtils.isEmpty(key) ? APP_KEY : key)), INPUT_CHARSET);
        logger.info("Before Signature Str：" + new String(contentBytes));
        String mysign = DigestUtils.md5Hex(contentBytes);
        sPara.put(PARAM_SIGN, mysign);
        return sPara;
    }

    /**
     * 单独获取签名sign字符串
     *
     * @param sParaTemp 签名参数数组
     * @return
     */
    public static String getSignStr(Map<String, String> sParaTemp) {
        return sign(sParaTemp).get("sign");
    }

    /**
     * 生成要请求的签名参数字符串“参数=参数值”&链接
     *
     * @param sParaTemp 需要签名的参数Map
     * @return 请求的签名参数字符串
     */
    public static String signStringMap(Map<String, String[]> sParaTemp) {
        //生成要请求的签名参数数组
        Map<String, String> sign = signMap(sParaTemp);
        //生成要请求的签名参数字符串“参数=参数值”&链接
        return createLinkString(sign, true);
    }

    /**
     * 生成要请求的签名参数字符串“参数=参数值”&链接
     *
     * @param sParaTemp 需要签名的参数
     * @return
     */
    public static String signString(Map<String, String> sParaTemp) {
        return signString(sParaTemp, null);
    }

    /**
     * 生成要请求的签名参数字符串“参数=参数值”&链接
     *
     * @param sParaTemp 需要签名的参数
     * @return
     */
    public static String signString(Map<String, String> sParaTemp, String key) {
        //生成要请求的签名参数数组
        Map<String, String> sign = sign(sParaTemp, key);
        //生成要请求的签名参数字符串“参数=参数值”&链接
        return createLinkString(sign, true);
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     *
     * @param paramsMap 通知返回来的请求参数Map
     * @return 验证结果
     */
    public static boolean verifyMap(Map<String, String[]> paramsMap) {
        return verify(toVerifyMap(paramsMap, false));
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     *
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {
        return verify(params, null);
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     *
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params, String key) {
        String sign = "";
        if (StringUtils.isEmpty(key)) key = APP_KEY;
        if (params.get(PARAM_SIGN) != null) {
            sign = params.get(PARAM_SIGN);
        } else {
            logger.info("：：：request sign is null - " + params);
            return false;
        }
        String timestamp = "";
        if (params.get(PARAM_TIMESTAMP) != null) {
            timestamp = params.get(PARAM_TIMESTAMP);
        } else {
            return false;
        }
        Map<String, String> sParaNew = paraFilter(params);
        String preSignStr = createLinkString(sParaNew);
        String mysign = DigestUtils.md5Hex(getContentBytes(preSignStr + key, INPUT_CHARSET));
        if (mysign.equals(sign)) {
//            long curr = System.currentTimeMillis() / 1000;
//            if ((curr - Long.valueOf(timestamp)) > TIME_OUT) { //是否超时
//                logger.info("：：：Dealer request api is time out - " + preSignStr);
//                return false;
//            }
            return true;
        } else {
            return false;
        }
    }

}
