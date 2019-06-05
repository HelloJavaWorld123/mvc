package com.jzy.api.util;

import com.rrtx.mer.bean.ProcessMessage;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class DigestUtil {


	private static String encodingCharset = "UTF-8";

	/**
	 * @param aValue
	 * @param aKey
	 * @return
	 */
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null) {
			return null;
		}
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16) {
				output.append("0");
			}
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 *
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacSign(str.toString(), key));
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));

	}
//
//	public static void main(String[] args) {
//		String tranData = new String(ProcessMessage.Base64Decode("PD94bWwgdmVyc2lvbj0gIjEuMCIgZW5jb2Rpbmc9IkdCSyIgPz48QjJDUmVxPjxtZXJjaGFudElkPk0xMDAwMDAwMjM8L21lcmNoYW50SWQ+PG9yZGVyTm8+RVUwNDgzMzkxMjA5MDcyNDQzPC9vcmRlck5vPjxvcmRlckFtdD4xLjAwPC9vcmRlckFtdD48Y3VyVHlwZT5DTlk8L2N1clR5cGU+PHJldHVyblVSTD5odHRwOi8vd3d3LmVkYWppYS5jb20vZ3JvdW5kL3BheVJlc3VsdDI8L3JldHVyblVSTD48bm90aWZ5VVJMPmh0dHA6Ly93d3cuZWRhamlhLmNvbS9ncm91bmQvU3VtcGF5T3JkZXJQYXltZW50MTwvbm90aWZ5VVJMPjxyZW1hcms+urzW3c73z6rKqrXYPC9yZW1hcms+PC9CMkNSZXE+"));
//		System.out.println(tranData);
//
//		System.out.println(hmacSign("12345678abcdef测试","qaWSedRF20120905"));
//		//System.out.println(hmacSign("<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq><merchantId>M100000046</merchantId><orderNo>EU0483391208014256</orderNo><orderAmt>30.00</orderAmt><curType>CNY</curType><returnURL>http://www.edajia.com/ground/payResult</returnURL><notifyURL>http://www.edajia.com/ground/SumpayOrderPayment</notifyURL><remark>杭州西溪湿地</remark></B2CReq>","123123"));
//	}

	public static void main(String[] args) {

		String tranDataStr = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48b3JkZXJSZXNwb25zZT4KPG9yZGVyTm8";
		String tranData = new String(ProcessMessage.Base64Decode(tranDataStr));
		System.out.println(tranData);

		String mersign = "DRMO/nGI3Dyd+AvIYU+4bzfinOIX1QIch/TA2KDSGm+B1hahWXGgeaOTIVvQRKAeW8Ikqc0s6C5+2QEmXIZ9EsTAjQnnqCIJuAgHeSbD4xzxAjOqqxJRWhbz2tY2GigybB0Dig8wVwJchjgll2D0BmInHib5HvoqhJ+M6w00qZz4SzTTRaqOSe3+6Tel5AGcUkeP0g4iI/T4jx0cNttTmL1u6lXPAwj62G0D+bqmxPLDium8GIDnjseRLG14aFfge0+FXcfEGTnHdF75Qkk9DvoTz9nBZ1+P4xLUI/U7U1+k53wGbe1V7qabZUnS8FC0a9VyTuU25gboq3aaHoP3iA==";

//		String strRealPath = XimeiPayConstants.PUBLIC_CERT_PATH;
//
//		boolean flag = ProcessMessage.verifyMessage(tranData, mersign, strRealPath);
//		System.out.println(flag);
		//System.out.println(hmacSign("<?xml version=\"1.0\" encoding=\"GBK\" ?><B2CReq><merchantId>M100000046</merchantId><orderNo>EU0483391208014256</orderNo><orderAmt>30.00</orderAmt><curType>CNY</curType><returnURL>http://www.edajia.com/ground/payResult</returnURL><notifyURL>http://www.edajia.com/ground/SumpayOrderPayment</notifyURL><remark>杭州西溪湿地</remark></B2CReq>","123123"));
	}
}
