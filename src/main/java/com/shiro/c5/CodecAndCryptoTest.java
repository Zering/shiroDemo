package com.shiro.c5;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.DefaultBlockCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;

/**
 * 编码/解码
 * 
 * @author zhanghaojie
 *
 */
public class CodecAndCryptoTest {

	@Test
	public void testBase64() {
		String str1 = "shiro";
		String base64Encode = Base64.encodeToString(str1.getBytes());
		System.out.println("Base64加密：" + base64Encode);
		String str2 = Base64.decodeToString(base64Encode);
		Assert.assertEquals(str1, str2);
	}

	/**
	 * 转16进制
	 */
	@Test
	public void testHex() {
		String str1 = "shiro";
		String hexEncode = Hex.encodeToString(str1.getBytes());
		System.out.println("16进制编码：" + hexEncode);
		String str2 = new String(Hex.decode(hexEncode));
		Assert.assertEquals(str1, str2);
	}

	/**
	 * 转字节
	 */
	@Test
	public void testCodecSurport() {
		String str1 = "shiro";
		byte[] bytes = CodecSupport.toBytes(str1, "utf-8");
		String str2 = CodecSupport.toString(bytes, "utf-8");
		Assert.assertEquals(str1, str2);
	}

	/**
	 * 生成随机数
	 */
	@Test
	public void testRandomNum() {
		SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
		generator.setSeed("123".getBytes());
		System.out.println(generator.nextBytes().toHex());
	}

	@Test
	public void testMd5() {
		String str = "shiro";
		String salt = "123";
		String md5 = new Md5Hash(str, salt).toString();
		System.out.println(md5);
	}

	@Test
	public void testSha1() {
		String str = "shiro";
		String salt = "123";
		String sha1 = new Sha1Hash(str, salt).toString();
		System.out.println(sha1);

	}

	@Test
	public void testSha256() {
		String str = "shiro";
		String salt = "123";
		String sha1 = new Sha256Hash(str, salt).toString();
		System.out.println(sha1);

	}

	@Test
	public void testSha384() {
		String str = "shiro";
		String salt = "123";
		String sha1 = new Sha384Hash(str, salt).toString();
		System.out.println(sha1);

	}

	@Test
	public void testSha512() {
		String str = "shiro";
		String salt = "123";
		String sha1 = new Sha512Hash(str, salt).toString();
		System.out.println(sha1);

	}

	@Test
	public void testSimpleHash() {
		String str = "shiro";
		String salt = "123";
		// MessageDigest
		String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
		System.out.println(simpleHash);

	}
	
	@Test
	public void testHashService(){
		DefaultHashService hashService = new DefaultHashService();
		hashService.setHashAlgorithmName("SHA-512");
		hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐
		hashService.setGeneratePublicSalt(true);
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator()); //默认
		hashService.setHashIterations(1); //生成hash的迭代次数
		
		HashRequest request = new HashRequest.Builder()
								.setAlgorithmName("MD5")
								.setSource(ByteSource.Util.bytes("shiro"))
								.setSalt(ByteSource.Util.bytes("123"))
								.setIterations(2)
								.build();
		
		String hex = hashService.computeHash(request).toHex();
		System.out.println(hex);
	}
	
	@Test
	public void testAesCipherService(){
		AesCipherService service = new AesCipherService();
		service.setKeySize(128);
		Key key = service.generateNewKey();
		String str = "shiro";
		//加密
		String hex = service.encrypt(str.getBytes(), key.getEncoded()).toHex();
		//解密
		String str2 = new String(service.decrypt(Hex.decode(hex), key.getEncoded()).getBytes());
		Assert.assertEquals(str, str2);
	}

	@Test
	public void testBlowfishCipherService() {
		BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
		blowfishCipherService.setKeySize(128);
		Key key = blowfishCipherService.generateNewKey();
		String text = "shiro";
		// 加密
		String encrptText = blowfishCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
		// 解密
		String text2 = new String(blowfishCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
		Assert.assertEquals(text, text2);
	}

	@Test
	public void testDefaultBlockCipherService() throws Exception {
		// 对称加密，使用Java的JCA（javax.crypto.Cipher）加密API，常见的如 'AES', 'Blowfish'
		DefaultBlockCipherService cipherService = new DefaultBlockCipherService("AES");
		cipherService.setKeySize(128);
		Key key = cipherService.generateNewKey();
		String text = "shiro";
		// 加密
		String encrptText = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
		// 解密
		String text2 = new String(cipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
		Assert.assertEquals(text, text2);
	}

}
