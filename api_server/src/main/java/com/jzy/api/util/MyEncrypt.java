package com.jzy.api.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MyEncrypt {

    public static final String TOKEN = "_900mall_Token";
    private static final MyEncrypt instance = new MyEncrypt();
    private MyEncrypt() {
    }
    public static MyEncrypt getInstance() {
        return instance;
    }

    /**
     * 返回十六进制字符串
     *
     * @param arr
     * @return
     */
    private String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,
                    3));
        }
        return sb.toString();
    }

    /**
     * 密码加密
     *
     * @param inputStr
     * @return
     */
    public String obscureMd5(String inputStr) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("^#").append(inputStr).append(TOKEN).append("#~");
        return md5(sbuf.toString());
    }

    /**
     * 密码加密
     *
     * @param inputStr
     * @param salt
     * @return
     */
    public String obscureMd5(String inputStr, String salt) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("^#").append(inputStr).append(salt).append("#~");
        return md5(sbuf.toString());
    }

    /**
     * 密码加密
     *
     * @param inputStr
     * @return
     */
    public String obscureSha(String inputStr) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("^#").append(inputStr).append(TOKEN).append("#~");
        return sha(sbuf.toString());
    }

    /**
     * 密码加密
     *
     * @param inputStr
     * @param salt
     * @return
     */
    public String obscureSha(String inputStr, String salt) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("^#").append(inputStr).append(salt).append("#~");
        return sha(sbuf.toString());
    }

    /**
     * md5加密
     *
     * @param inputStr
     * @return
     */
    public String md5(String inputStr) {
        return encrypt(inputStr, "md5");
    }

    /**
     * sha加密
     *
     * @param inputStr
     * @return
     */
    public String sha(String inputStr) {
        return encrypt(inputStr, "sha-1");
    }

    /**
     * md5或者sha-1加密
     *
     * @param inputStr      要加密的内容
     * @param algorithmName 加密算法名称：md5或者sha-1，不区分大小写
     * @return
     */
    private String encrypt(String inputStr, String algorithmName) {
        if (inputStr == null || "".equals(inputStr.trim())) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputStr.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputStr.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptText;
    }

    /**
     * 生成Token TOKEN：MzhhMWEwODliYjJkOGI3ODU2Y2JiYWZiNDUzNDJmYjA=
     *
     * @return
     */
    public String token() {
        StringBuffer sbr = new StringBuffer();
        sbr.append("^#").append(System.nanoTime()).append(TOKEN).append("#~");
        StringBuilder sb = new StringBuilder(DigestUtils.md5Hex(sbr.toString().getBytes()));
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    /**
     * 生成Token TOKEN:b170923c4dd4240a0517fd6d34afc1c5f14e436a
     *
     * @param uid
     * @param time
     * @param rand
     * @return
     */
    public String token(Long uid, Long time, String rand) {
        return sha(new StringBuffer().append("^#").append(uid)
                .append(time).append(rand).append("#~").toString());
    }

    /**
     * 验证
     *
     * @param request
     * @return
     */
    public boolean isRepeatSubmit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String client_token = request.getParameter(TOKEN);
        while (client_token == null) {
            return true;
        }
        String server_token = (String) session.getAttribute(TOKEN);
        while (server_token == null || !client_token.equals(server_token)) {
            return true;
        }
        session.removeAttribute(TOKEN);
        return false;
    }

}
