package com.royal.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.royal.util.SecurityUtil.RsaUtil.RsaKeyPair;
import org.springframework.util.Base64Utils;

public class SecurityUtil {

	/**
	 * 对称加密算法
	 *
	 *
	 */
	public static class AesUtil {
		private static final String ALGORITHM = "AES";
		private static final String DEFAULT_CHARSET = "UTF-8";

		/**
		 * 生成秘钥
		 *
		 * @return
		 * @throws NoSuchAlgorithmException
		 */
		public static String generaterKey() throws NoSuchAlgorithmException {
			KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
			keygen.init(128, new SecureRandom()); // 16 字节 == 128 bit
			// keygen.init(128, new SecureRandom(seedStr.getBytes())); // 随机因子一样，生成出来的秘钥会一样
			SecretKey secretKey = keygen.generateKey();
			return Base64Utils.encodeToString(secretKey.getEncoded());
		}

		/**
		 */
		public static SecretKeySpec getSecretKeySpec(String secretKeyStr) {
			byte[] secretKey = Base64Utils.decodeFromString(secretKeyStr);
//			System.out.println(secretKey.length);
			return new SecretKeySpec(secretKey, ALGORITHM);
		}

		/**
		 * 加密
		 */
		public static String encrypt(String content, String secretKey) throws Exception {
			Key key = getSecretKeySpec(secretKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));
			return Base64Utils.encodeToString(result);
		}

		/**
		 * 解密
		 */
		public static String decrypt(String content, String secretKey) throws Exception {
			Key key = getSecretKeySpec(secretKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));
			return new String(result);
		}
	}

	/**
	 * 非对称加密算法
	 *
	 *
	 */
	public static class RsaUtil {

		public static class RsaKeyPair {
			private String publicKey = "";
			private String privateKey = "";

			public RsaKeyPair(String publicKey, String privateKey) {
				super();
				this.publicKey = publicKey;
				this.privateKey = privateKey;
			}

			public String getPublicKey() {
				return publicKey;
			}

			public String getPrivateKey() {
				return privateKey;
			}
		}

		private static final String ALGORITHM = "RSA";
		private static final String ALGORITHMS_SHA1WithRSA = "SHA1WithRSA";
		private static final String ALGORITHMS_SHA256WithRSA = "SHA256WithRSA";
		private static final String DEFAULT_CHARSET = "UTF-8";

		private static String getAlgorithms(boolean isRsa2) {
			return isRsa2 ? ALGORITHMS_SHA256WithRSA : ALGORITHMS_SHA1WithRSA;
		}

		/**
		 * 生成秘钥对
		 *
		 * @return
		 * @throws NoSuchAlgorithmException
		 */
		public static RsaKeyPair generaterKeyPair() throws NoSuchAlgorithmException {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
			SecureRandom random = new SecureRandom();
			// SecureRandom random = new SecureRandom(seedStr.getBytes()); //
			// 随机因子一样，生成出来的秘钥会一样
			// 512位已被破解，用1024位,最好用2048位
			keygen.initialize(2048, random);
			// 生成密钥对
			KeyPair keyPair = keygen.generateKeyPair();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			String privateKeyStr = Base64Utils.encodeToString(privateKey.getEncoded());
			String publicKeyStr = Base64Utils.encodeToString(publicKey.getEncoded());
			return new RsaKeyPair(publicKeyStr, privateKeyStr);
		}

		/**
		 * 获取公钥
		 *
		 * @param publicKey
		 * @return
		 * @throws Exception
		 */
		public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
			byte[] keyBytes = Base64Utils.decodeFromString(publicKey);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			return (RSAPublicKey) keyFactory.generatePublic(spec);
		}

		/**
		 * 获取私钥
		 *
		 * @param privateKey
		 * @return
		 * @throws NoSuchAlgorithmException
		 * @throws InvalidKeySpecException
		 * @throws Exception
		 */
		public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
			byte[] keyBytes = Base64Utils.decodeFromString(privateKey);
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			return (RSAPrivateKey) keyFactory.generatePrivate(spec);
		}

		/**
		 * 用私钥签名
		 *
		 * @throws InvalidKeySpecException
		 * @throws Exception
		 */
		public static String sign(String content, String privateKey, boolean isRsa2) throws Exception {
			PrivateKey priKey = getPrivateKey(privateKey);
			java.security.Signature signature = java.security.Signature.getInstance(getAlgorithms(isRsa2));
			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));
			byte[] signed = signature.sign();
			return Base64Utils.encodeToString(signed);
		}


		/**
		 * 用公钥验签
		 */
		public static boolean verify(String content, String sign, String publicKey, boolean isRsa2) throws Exception {
			PublicKey pubKey = getPublicKey(publicKey);
			java.security.Signature signature = java.security.Signature.getInstance(getAlgorithms(isRsa2));
			signature.initVerify(pubKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));
			return signature.verify(Base64Utils.decodeFromString(sign));
		}

		/**
		 * 加密
		 *
		 * @param content
		 * @param pubOrPrikey
		 * @return
		 */
		public static String encrypt(String content, Key pubOrPrikey) throws Exception {
			Cipher cipher = null;
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, pubOrPrikey);
			byte[] result = cipher.doFinal(content.getBytes(DEFAULT_CHARSET));
			return Base64Utils.encodeToString(result);
		}

		/**
		 * 解密
		 *
		 * @param content
		 * @param pubOrPrikey
		 * @return
		 */
		public static String decrypt(String content, Key pubOrPrikey) throws Exception {
			Cipher cipher = null;
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, pubOrPrikey);
			byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));
			return new String(result);
		}
	}

	public static void main(String[] args) {

//		System.out.println("-----------<<< testAes >>>------------------");
//		try {
//			String content = "testAes";
//			String secretKeyStr = SecurityUtil.AesUtil.generaterKey();
//			System.out.println("-----------secretKeyStr------------------");
//			System.out.println(secretKeyStr);
//			String encryptStr = SecurityUtil.AesUtil.encrypt(content, secretKeyStr);
//			String decryptStr = SecurityUtil.AesUtil.decrypt(encryptStr, secretKeyStr);
//			System.out.println("-----------encryptStr------------------");
//			System.out.println(encryptStr);
//			System.out.println("-----------decryptStr------------------");
//			System.out.println(decryptStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		System.out.println("-----------<<< testRsa >>>------------------");
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("21ddf",222);
			map.put("sd333",333);
			map.put("hhh444",111);
			map.put("34sss",444);
			String content = Tools.formatParam(map);
			System.out.println(content);
			// 生成秘钥对
			RsaKeyPair mRsaKeyPair = SecurityUtil.RsaUtil.generaterKeyPair();
			String privateKeyStr = mRsaKeyPair.getPrivateKey();
			String publicKeyStr = mRsaKeyPair.getPublicKey();
			System.out.println("-----------privateKeyStr------------------");
			System.out.println(privateKeyStr);
			System.out.println("-----------publicKeyStr------------------");
			System.out.println(publicKeyStr);

			// test sign
			{
				String signStr = SecurityUtil.RsaUtil.sign(content, privateKeyStr, true);
				boolean isValid = SecurityUtil.RsaUtil.verify(content, signStr,
						publicKeyStr, true);
				System.out.println("-----------signStr------------------");
				System.out.println(signStr);
				System.out.println("-----------isValid------------------");
				System.out.println(isValid);
			}

			// test codec
			{
				Key privateKey = SecurityUtil.RsaUtil.getPrivateKey(privateKeyStr);
				Key publicKey = SecurityUtil.RsaUtil.getPublicKey(publicKeyStr);

				// 私钥加密、公钥解密
				String encryptStr = SecurityUtil.RsaUtil.encrypt(content, privateKey);
				String decryptStr = SecurityUtil.RsaUtil.decrypt(encryptStr, publicKey);
				// Assert.assertEquals(content, decryptStr);
				System.out.println("-----------encryptStr------------------");
				System.out.println(encryptStr);
				System.out.println("-----------decryptStr------------------");
				System.out.println(decryptStr);

				// 公钥加密、私钥解密
				encryptStr = SecurityUtil.RsaUtil.encrypt(content, privateKey);

				decryptStr = SecurityUtil.RsaUtil.decrypt(encryptStr, publicKey);
			}
		}catch (Exception e){

		}

	}
}
