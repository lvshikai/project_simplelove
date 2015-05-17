package com.example.easylove.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * AES加解密工具类
 * @author QLong
 * @since 2014-8-15
 * @thanks JianZhuChen
 */
public class AESUtil {
	
	public static final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";
	public static final int HASH_ITERATIONS = 10000;
	public static final int KEY_LENGTH = 256;
	public static byte[] salt = { 1, 3, 9, 6, 9, 4, 4, 4, 0, 2, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF };
	public static final String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";
	public static SecretKeyFactory keyfactory = null;
	// public static byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6,
	// 8, 0xC, 0xD, 91 };
	public static IvParameterSpec ivSpec = new IvParameterSpec(new byte[] { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91 });

//    private static Log log = LogFactory.getLog(AESUtil.class);
	
	public synchronized static void createSecretKeyFactory() {
		if (keyfactory == null) {
			try {
				keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			} catch (NoSuchAlgorithmException e) {
				//log.error("no key factory support for PBEWITHSHAANDTWOFISH-CBC");
			}
		}
	}

	public static SecretKeySpec aesKeyConvert(String key) {
		try {
			PBEKeySpec myKeyspec = new PBEKeySpec(key.toCharArray(), salt, HASH_ITERATIONS, KEY_LENGTH);
			if (keyfactory == null)
				createSecretKeyFactory();
			SecretKey sk = keyfactory.generateSecret(myKeyspec);
			byte[] skAsByteArray = sk.getEncoded();
			SecretKeySpec skforAES = new SecretKeySpec(skAsByteArray, "AES");
			return skforAES;
		} catch (InvalidKeySpecException ikse) {
//			log.error("invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
		}
		return null;
	}

	public static String encrypt(byte[] plaintext, String password) {
		SecretKeySpec skforAES = aesKeyConvert(password);
		byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, ivSpec, plaintext);
		String base64_ciphertext = Base64.encode(ciphertext);
		return base64_ciphertext;
	}

//	public static String decrypt(String ciphertext_base64, String password) {
//		byte[] s = Base64.decodeToBytes(ciphertext_base64);
//		SecretKeySpec skforAES = aesKeyConvert(password);
//		String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, ivSpec, s));
//		return decrypted;
//	}

	public static String decrypt(byte[] data, String password) {
		SecretKeySpec skforAES = aesKeyConvert(password);
		String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, ivSpec, data));
		return decrypted;
	}

	/**
	 * 加密
	 * 
	 * @param cmp
	 *            填充方式
	 * @param sk
	 *            密钥
	 * @param IV
	 *            向量
	 * @param msg
	 *            需要加密的内容
	 * @return 返回加密结果
	 */
	public static byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] msg) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.ENCRYPT_MODE, sk, IV);
			return c.doFinal(msg);
		} catch (NoSuchAlgorithmException e) {
		//	log.error("",e);
		} catch (NoSuchPaddingException e) {
		//	log.error("",e);
		} catch (InvalidKeyException e) {
		//	log.error("",e);
		} catch (InvalidAlgorithmParameterException e) {
			//log.error("",e);
		} catch (IllegalBlockSizeException e) {
		//	log.error("",e);
		} catch (BadPaddingException e) {
		//	log.error("",e);
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param cmp
	 *            填充函数
	 * @param sk
	 *            密钥
	 * @param IV
	 *            向量
	 * @param ciphertext
	 *            需要解密内容
	 * @return 返回解密结果
	 */
	public static byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			return c.doFinal(ciphertext);
		} catch (NoSuchAlgorithmException nsae) {
			//log.error("",nsae);
		} catch (NoSuchPaddingException nspe) {
		//	log.error("",nspe);
		} catch (InvalidKeyException e) {
		//	log.error("",e);
		} catch (InvalidAlgorithmParameterException e) {
			//log.error("",e);
		} catch (IllegalBlockSizeException e) {
			//log.error("",e);
		} catch (BadPaddingException e) {
			//log.error("",e);
		}
		return null;
	}
}
