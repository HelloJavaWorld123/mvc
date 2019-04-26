package com.jzy.api.util;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyStringUtil {

    /**
     * 缓存前缀，用于Memcached缓存分组
     */
    public static final String CACHE_CAPTCHA = "captcha";
    public static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBER_CHAR = "0123456789";
    public static final String NUMBER_CHAR_NO = "123456789";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯数字字符串(只包含数字)
     *
     * @param length 随机字符串长度
     * @return
     */
    public static String generateNumString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(NUMBER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯数字字符串(只包含数字)
     *
     * @param length 随机字符串长度
     * @return
     */
    public static String generateNumStringNo(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(NUMBER_CHAR_NO.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }

    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */

    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length 字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num       数字
     * @param fixdlenth 字符串长度
     * @return 定长的字符串
     */

    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = MyStringUtil.getString(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常!");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * Object转换成Long
     *
     * @param obj
     * @return
     */
    public static Long getLong(Object obj) {
        return getLong(obj, 0L);
    }

    /**
     * Object转换成Long
     *
     * @param obj
     * @return
     */
    public static Long getLong(Object obj, Long defaultValue) {
        try {
            return Long.valueOf(obj + "");
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Object转换成Integer
     *
     * @param obj
     * @return
     */
    public static Integer getInteger(Object obj) {
        return getInteger(obj, 0);
    }

    /**
     * Object转换成Integer
     *
     * @param obj
     * @return
     */
    public static Integer getInteger(Object obj, Integer defaultValue) {
        try {
            return Integer.valueOf(obj + "");
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Object转换成String
     *
     * @param obj
     * @return
     */
    public static String getString(Object obj) {
        return getString(obj, "");
    }

    /**
     * Object转换成String
     *
     * @param obj
     * @return
     */
    public static String getString(Object obj, String defaultValue) {
        try {
            if (obj == null) {
                return defaultValue;
            }
            return String.valueOf(obj);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Object转换成Double
     *
     * @param obj
     * @return
     */
    public static Double getDouble(Object obj) {
        try {
            if (obj == null) {
                return 0.00;
            }
            return Double.valueOf(getString(obj));
        } catch (Exception e) {
            return 0.00;
        }
    }

    /**
     * Object转换成Boolean
     *
     * @param obj
     * @return
     */
    public static Boolean getBoolean(Object obj) {
        try {
            return Boolean.valueOf(obj + "");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断一字符串是否为null 或者空字符串 如果都不是则为true,否则false;
     *
     * @param str
     * @return
     */
    public static boolean strIsNullOrEq(String str) {
        if (str != null) {
            if (!str.equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串不为null 再和另一字符串比较  如果相等则为true,否则false;
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean strIsNullAndEqStr(String str1, String str2) {
        if (str1 != null) {
            while (str1.equals(str2)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isNotEmpty(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return true;
    }

    public static boolean isIncludeStr(String str1, String str2) {

        if (str1 != null && str2 != null) {
            if (str1.indexOf(str2) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * url编码-utf-8
     *
     * @param str 编码前数据
     * @return 编码后数据
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
     * url解码-utf-8
     *
     * @param str 解码前数据
     * @return 解码后数据
     */
    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 正则表达式判断字符串是否是数字
     *
     * @param str
     * @param l 指定长度
     * @return
     */
    public static boolean isNumeric(String str, int l) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) return false;
        if (l > 0) return str.length() == l;
        return true;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return isNumeric(str, 0);
    }

    /**
     * 转换int数组并排序
     *
     * @param str
     * @return
     */
    public static Integer[] convertIntArray(String str) {
        return convertIntArray(str, null, true);
    }

    /**
     * 转换int数组
     *
     * @param str
     * @param beSort 是否排序
     * @return
     */
    public static Integer[] convertIntArray(String str, boolean beSort) {
        return convertIntArray(str, null, beSort);
    }

    /**
     * 字符串转换int数组
     *
     * @param str
     * @param regex 指定分割字符
     * @param beSort 是否排序
     * @return
     */
    public static Integer[] convertIntArray(String str, String regex, boolean beSort) {
        regex = StringUtils.isEmpty(regex) ? "," : regex;
        Integer arr[];
        if (null == str) {
            arr = new Integer[0];
        } else {
            str = str.replaceAll(" ", "");
            String[] strArr = str.split(regex);
            arr = convertIntArray(strArr);
            if (beSort) Arrays.sort(arr);
        }
        return arr;
    }

    /**
     * String[] -> Integer[]
     *
     * @param strArr
     * @return
     */
    public static Integer[] convertIntArray(String[] strArr) {
        if (Objects.nonNull(strArr)) {
            Integer arr[] = new Integer[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                arr[i] = Integer.parseInt(strArr[i]);
            }
            return arr;
        }
        return new Integer[0];
    }

}
