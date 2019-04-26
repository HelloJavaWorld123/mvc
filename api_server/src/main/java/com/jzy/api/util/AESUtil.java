package com.jzy.api.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class AESUtil {

    private static final String ALGORITHM = "AES/ECB/PKCS7Padding"; // AES_256_cbc pkcs7
    private static final String CHAR_ENCODING = "utf-8";

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] AES_ecb_encrypt(byte[] srcData, byte[] key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encData = cipher.doFinal(srcData);
        return encData;
    }

    private static byte[] AES_ecb_decrypt(byte[] encData, byte[] key) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decbbdt = cipher.doFinal(encData);
        return decbbdt;
    }

    private static byte[] AES_cbc_encrypt(byte[] srcData, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] encData = cipher.doFinal(srcData);
        return encData;
    }

    private static byte[] AES_cbc_decrypt(byte[] encData, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] decbbdt = cipher.doFinal(encData);
        return decbbdt;
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptWithKeyBase64(String data, String key) {
        try {
            key = MyEncrypt.getInstance().md5(key).toLowerCase();
            byte[] originalData = (data.getBytes());
            byte[] valueByte = AES_ecb_encrypt(originalData, key.getBytes());
            return new String(Base64.encode(valueByte));
        } catch (Exception e) {
            throw new RuntimeException("AES Encrypt Fail!", e);
        }
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptWithKeyBase64(String data, String key) {
        try {
            key = MyEncrypt.getInstance().md5(key).toLowerCase();
            byte[] originalData = Base64.decode(data.getBytes());
            byte[] valueByte = AES_ecb_decrypt(originalData, key.getBytes());
            return new String(valueByte, CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("AES Decrypt Fail!", e);
        }
    }

    private static byte[] getIv(byte[] key) {
        byte[] iv = new byte[16];
        for (int i = 0; i < 16; i++)
            iv[i] = key[i];
        return iv;
    }

}
