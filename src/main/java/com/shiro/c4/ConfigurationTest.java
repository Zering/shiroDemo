package com.shiro.c4;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * 配置与{@link com.shiro.c4.NoConfigurationTest} 等价
 * 
 * @author zhanghaojie
 *
 */
public class ConfigurationTest {

	@Test
	public void configurationTest(){
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:c4/shiro-config.ini");
		SecurityManager manager = factory.getInstance();

		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		subject.login(token);
		Assert.assertTrue(subject.isAuthenticated());
	}
}
