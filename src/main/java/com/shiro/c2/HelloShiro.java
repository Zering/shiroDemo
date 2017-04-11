package com.shiro.c2;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Hello shiro Attention:SecurityManager是org.apache.shiro.mgt下的 不是java.lang下的
 * 
 * @author zhanghaojie
 *
 */
public class HelloShiro {

	/**
	 * shiro入门--基本流程
	 */
	@Test
	public void helloWorld() {
		// 注册绑定登录信息到SecurityUtils
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:c2/shiro.ini"); // 一般会用数据库
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		// 验证 这里使用用户名+密码的方式 支持多种方式 如邮箱、手机号等等
		AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("Login failed");
		}

		Assert.assertTrue("user zhang is not logined", subject.isAuthenticated());// 断言用户zhang已登录

		subject.logout();// 登出
		Assert.assertFalse("user zhang is online", subject.isAuthenticated());// 断言登出
	}

	/**
	 * realm可以理解为数据源 单realm表示只有一种登录方式 如只支持帐号密码登录 基本流程与入门类似 只是ini配置不同
	 * 
	 */
	@Test
	public void singleRealmDemo() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:c2/shiro-realm.ini");
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		// 验证 这里使用用户名+密码的方式 支持多种方式 如邮箱、手机号等等
		AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("Login failed");
		}

		Assert.assertTrue("user zhang is not logined", subject.isAuthenticated());// 断言用户zhang已登录

		subject.logout();// 登出
		Assert.assertFalse("user zhang is online", subject.isAuthenticated());// 断言登出
	}

	/**
	 * 多realm
	 */
	@Test
	public void multiRealmDemo() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:c2/shiro-multi-realm.ini");
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		// 验证 这里使用用户名+密码的方式 支持多种方式 如邮箱、手机号等等
		AuthenticationToken token = new UsernamePasswordToken("wang", "123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("Login failed");
		}

		Assert.assertTrue("user wang is not logined", subject.isAuthenticated());// 断言用户wang已登录

		subject.logout();// 登出
		Assert.assertFalse("user wang is online", subject.isAuthenticated());// 断言登出
	}

	/**
	 * 数据库realm 自带 只需要写配置
	 */
	@Test
	public void jdbcRealmDemo() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:c2/shiro-jdbc-realm.ini");
		SecurityManager manager = factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		// 验证 这里使用用户名+密码的方式 支持多种方式 如邮箱、手机号等等
		AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println("Login failed");
		}

		Assert.assertTrue("user zhang is not login", subject.isAuthenticated());// 断言用户已登录

		subject.logout();// 登出
		Assert.assertFalse("user zhang is online", subject.isAuthenticated());// 断言登出
	}

	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}
}
