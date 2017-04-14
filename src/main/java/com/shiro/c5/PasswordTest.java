package com.shiro.c5;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.shiro.c3.BaseTest;

public class PasswordTest extends BaseTest {

	@Test
	public void testPasswordServiceWithMyRealm() {
		Subject subject = login("classpath:c5/shiro-passwordservice.ini", "wu", "123");
		Assert.assertTrue(subject.isAuthenticated());
	}

	@Test
	public void testGeneratorPassword() {
		String algorithmName = "md5";
		String username = "liu";
		String password = "123";
		String salt1 = username;
		String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
		int hashIterations = 2;

		SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
		String encodedPassword = hash.toHex();
		// 1b889a90cb18e42a3cbf4b3ba8768e9b
		System.out.println(salt2);
		// 059e6cc0cb8898950e0a275e7cbe401f
		System.out.println(encodedPassword);
	}

	@Test
	public void testHashedCredentialsMatcher() {
		Subject subject = login("classpath:c5/shiro-hashedCredentialsMatcher.ini", "liu", "123");
		Assert.assertTrue(subject.isAuthenticated());
	}

	@Test
	public void testJdbcHashedCredentialsMatcher() {
		BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);
		Subject subject = login("classpath:c5/shiro-jdbc-hashedCredentialsMatcher.ini", "liu", "123");
		Assert.assertTrue(subject.isAuthenticated());
	}

	private class EnumConverter extends AbstractConverter {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected Object convertToType(Class type, Object value) throws Throwable {
			return Enum.valueOf(type, value.toString());
		}

		@SuppressWarnings("rawtypes")
		@Override
		protected Class getDefaultType() {
			return null;
		}

		@SuppressWarnings("rawtypes")
		@Override
		protected String convertToString(Object value) throws Throwable {
			return ((Enum) value).name();
		}

	}

	@Test
	public void testTryLimitCredentialsMatcher() {
		for (int i = 0; i < 5; i++) {
			try {
				login("classpath:c5/shiro-retryLimitCredentialsMatcher.ini", "liu", "234");
			} catch (IncorrectCredentialsException e) {
				// 密码错误 do nothing
			}
		}
		// org.apache.shiro.authc.ExcessiveAttemptsException
		// 超过尝试次数
		login("classpath:c5/shiro-retryLimitCredentialsMatcher.ini", "liu", "234");

	}

}
