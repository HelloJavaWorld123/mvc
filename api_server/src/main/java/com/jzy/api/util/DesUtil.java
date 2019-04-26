package com.jzy.api.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class DesUtil{

    private static Logger logger = Logger.getLogger(DesUtil.class);
    private final static String DES = "DES";
    public final static String KEY_KEY = "A1B2C3D4E5F60708";
    public final static String KEY_LOTTERY = "E5F60708A1B2C3D4";//E5F60708A1B2C3D4


    // DES3加密格式
    private static final String DESC3 = "DESede";
    private static final String Algorithm = "DESede/ECB/PKCS5Padding";

    /**
     * DESC3加密
     *
     * @param src 加密数据
     * @param key 密钥
     * @return
     */
    public static String des3Eencrypt(String src, String key) {
        String ss = "";

        try {
            byte[] b = Base64.decodeBase64(key);
            DESedeKeySpec spec = new DESedeKeySpec(b);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DESC3);
            Key deskey = keyfactory.generateSecret(spec);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(1, deskey);
            b = c1.doFinal(src.getBytes());
            ss = Base64.encodeBase64String(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ss;
    }

    /**
     * DESC3解密
     *
     * @param src      解密数据
     * @param key      密钥
     * @param encoding 编码格式
     * @return
     */
    public static String des3Decrypt(String src, String key, String encoding) {
        try {
            byte[] s = Base64.decodeBase64(src);
            byte[] b = Base64.decodeBase64(key);
            DESedeKeySpec spec = new DESedeKeySpec(b);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DESC3);
            Key deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(2, deskey);
            byte[] decryptData = cipher.doFinal(s);
            return new String(decryptData, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) {
        String strs = null;
        try {
            byte[] bt = encrypt(data.getBytes(), key.getBytes());
            strs = new BASE64Encoder().encode(bt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) {
        byte[] bt = null;
        try {
            if (data == null)
                return null;
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            bt = decrypt(buf, key.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        try {
            // 生成一个可信任的随机数源
            SecureRandom sr = new SecureRandom();

            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);

            // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey securekey = keyFactory.generateSecret(dks);

            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);

            // 用密钥初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

            return cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String appId = "001";
        String activityCode = "0001";
        String mobilePhone = "13693678938";
        String prizeCode = "1";
        String name = "徐文鑫阿斯蒂芬爱的色放";
        String addr = "xxxx";

        System.out.println("appId:" + DesUtil.encrypt(appId, DesUtil.KEY_LOTTERY));
        System.out.println("activityCode:" + DesUtil.encrypt(activityCode, DesUtil.KEY_LOTTERY));
        System.out.println("mobilePhone:" + DesUtil.encrypt(mobilePhone, DesUtil.KEY_LOTTERY));
        System.out.println("prizeCode:" + DesUtil.encrypt(prizeCode, DesUtil.KEY_LOTTERY));
        System.out.println("name:" + DesUtil.encrypt(name, DesUtil.KEY_LOTTERY));
        System.out.println("name:" + URLEncoder.encode(DesUtil.encrypt(name, DesUtil.KEY_LOTTERY)));
        System.out.println("addr:" + DesUtil.encrypt(addr, DesUtil.KEY_LOTTERY));

        System.out.println(DesUtil.decrypt("y9VhmiHzKxuIUl/1NyN5lITLh+UkyLxczVmnbGj+I6JWtnUZ4nIPNg==", DesUtil.KEY_LOTTERY));


    }

}
