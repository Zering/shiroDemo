package com.shiro.c2;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
/**
 * 添加策略的验证
 * 
 * @author zhanghaojie
 *
 */
public class AuthenticatorTest {

	@Test
	public void allPassStrategy() {
		login("classpath:c2/shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();
		// 得到一个身份集合，其包含了Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(2, principalCollection.asList().size());
		Set<String> realmNames = principalCollection.getRealmNames();
		for (String string : realmNames) {
			System.out.println(string);
		}
	}

	@Test(expected = UnknownAccountException.class)
	public void allPassStrategyFail() {
		login("classpath:c2/shiro-authenticator-all-fail.ini");
	}

	private void login(String conf) {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(conf);
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
		subject.login(token);
	}

	@After
	public void tearDown() {
		ThreadContext.unbindSubject();
	}

}
