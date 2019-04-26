package com.jzy.api.util;

import org.springframework.util.DigestUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommUtils {

    public static final String REGEX_MOBILE = "^1[345789]\\d{9}$";
    private static Random random = new Random();

    /**
     * MD5 ENCRYPTION
     */
    public static String encryption(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * Uniqueness string()
     */
    public static String uniqueOrderStr() {
        StringBuffer orderStr = new StringBuffer(DateUtils.getDateStr());
        String ts = uniqueStr();
        return orderStr.append(ts.substring(6, ts.length())).toString();
    }

    /**
     * Uniqueness string(System.nanoTime())
     */
    public static String uniqueStr() {
        return String.valueOf(System.nanoTime());
    }

    /**
     * AUTH CODE
     */
    public static String authCode() {
        int authCode = (int) ((random.nextDouble() * 9 + 1) * 100000);
        return String.valueOf(authCode);
    }

    /**
     * UUID REPLACEALL("-", "") TOLOWERCASE
     */
    public static String lowerUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * UUID REPLACEALL("-", "") TOUPPERCASE
     */
    public static String upperUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 判断请求来自PC端|移动端
     *
     * @param requestHeader
     * @return
     */
    public static Boolean ismobiledevice(String requestHeader) {
        String[] deviceArr = new String[]{"Android", "iPhone OS", "Windows Phone"};
        if (requestHeader == null) {
            return false;
        }
        for (int i = 0; i < deviceArr.length; i++) {
            if (requestHeader.toUpperCase().indexOf(deviceArr[i].toUpperCase()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成随机用户名，数字和字母组成
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        StringBuffer val = new StringBuffer("");
        String charOrNum;
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * 获取指定路径文件的类型
     *
     * @param path
     * @return
     */
    public static String getContentType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String content_type = fileNameMap.getContentTypeFor(path);
        if (content_type == null) {
            content_type = new MimetypesFileTypeMap().getContentType(new File(path));
        }
        return content_type;
    }

    /**
     * 判断arr数组是否包含targetValue
     *
     * @param arr
     * @param targetValue
     * @return true:包含
     */
    public static boolean exist(Object[] arr, Object targetValue) {
        for (Object o : arr)
            if (o.equals(targetValue))
                return true;
        return false;
    }

    /**
     * 判断数组arro是否包含arrt
     *
     * @param arro not null 父
     * @param arrt not null 子
     * @return
     */
    public static boolean contains(Object[] arro, Object[] arrt) {
        List objarrt = new ArrayList<Object>(Arrays.asList(arrt));
        if (null != arro && arro.length > 0 && null != arrt) {
            for (int i = 0; i < arrt.length; i++) {
                if (Arrays.binarySearch(arro, arrt[i]) < 0) return false;
                objarrt.remove(arrt[i]);
            }
            return objarrt.size() > 0 ? false : true;
        } else {
            return false;
        }
    }

    /**
     * Emoji表情过滤
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source == null) {
            return source;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll("*");
            return source;
        }
        return source;
    }

    /**
     * 手机号格式验证
     *
     * @param mobile
     * @return
     */
    public static boolean ismobile(String mobile) {
        return mobile.matches(REGEX_MOBILE);
    }

    /**
     * 是否为微信内置浏览器
     *
     * @param request
     * @return
     */
    public static boolean iswechat(HttpServletRequest request) {
        return request.getHeader("user-agent").toLowerCase().indexOf("micromessenger") > -1;
    }

}
