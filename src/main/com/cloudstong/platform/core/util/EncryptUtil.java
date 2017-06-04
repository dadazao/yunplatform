package com.cloudstong.platform.core.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Allan Created on 2012-11-20
 * 
 *         Revision History: Date Reviser Description ---- -------
 *         ----------------------------------------------------
 * 
 *         Description:加密工具
 */
public class EncryptUtil {
	/**
	 * <code>key</code> 秘钥.
	 */
	private static Key key;

	/**
	 * 初始化Key
	 */
	static {
		try {
			String strKey = "yunplatform";
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
			keyFactory.generateSecret(keySpec);
			EncryptUtil.key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause:" + e);
		}
	}

	/**
	 * Description:MD5加密
	 * 
	 * @param plain
	 * @return
	 */
	public static String Md5(String plain) {
		String str = DigestUtils.md5Hex(plain);
		return str;
	}

	/**
	 * Description:HEX加密
	 * 
	 * @param plain
	 * @return
	 */
	public static String shaHex(String plain) {
		return DigestUtils.shaHex(plain);
	}

	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 根据参数生成KEY
	 */
	public static void setKey(String strKey) {
		try {
			if (strKey == null) {
				strKey = "yunplatform";
			}
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(strKey.getBytes());
			keyFactory.generateSecret(keySpec);
			EncryptUtil.key = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error initializing SqlMap class. Cause:" + e);
		}
	}

	/**
	 * 获得密钥
	 * 
	 * @param secretKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 */
	private SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
		keyFactory.generateSecret(keySpec);
		return keyFactory.generateSecret(keySpec);
	}

	/**
	 * 加密String明文输入,String密文输出
	 */
	public static String getEncString(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = EncryptUtil.getEncCode(byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause:" + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public static String getDesString(String strMi) {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = EncryptUtil.getDesCode(byteMi);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause:" + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private static byte[] getEncCode(byte[] byteS) {
		if (key == null) {
			setKey(null);
		}
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause:" + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private static byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException("Error initializing SqlMap class. Cause:" + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public static void main(String[] args) {
		// System.out.println("===>" + encryptSha256("123"));
		 System.out.println("===>" + Md5("sys_student").length());
	}

}
