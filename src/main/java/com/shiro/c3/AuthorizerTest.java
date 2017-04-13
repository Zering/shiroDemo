package com.shiro.c3;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

import junit.framework.Assert;

/**
 * 自定义权限验证
 * 
 * @author zhanghaojie
 *
 */
public class AuthorizerTest extends BaseTest {

	@Test
	public void testIsPermitted() {
		Subject subject = login("classpath:c3/shiro-authorizer.ini", "zhang", "123");
		Assert.assertTrue(subject.isPermitted("user1:"));
		Assert.assertTrue(subject.isPermitted("user1:update"));
		Assert.assertTrue(subject.isPermitted("user2:update"));
		// 通过二进制位的方式表示权限
		Assert.assertTrue(subject.isPermitted("+user1+2"));// 新增权限
		Assert.assertTrue(subject.isPermitted("+user1+8"));// 查看权限
		Assert.assertTrue(subject.isPermitted("+user2+10"));// 新增及查看

		Assert.assertFalse(subject.isPermitted("+user1+4"));// 没有删除权限

		Assert.assertTrue(subject.isPermitted("menu:view"));
	}
}
