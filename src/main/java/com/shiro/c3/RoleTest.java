package com.shiro.c3;

import java.util.Arrays;

import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

/**
 * 角色权限验证
 * 
 * @author zhanghaojie
 *
 */
public class RoleTest extends BaseTest{

	@Test
	public void hasRole(){
		Subject subject = login("classpath:c3/shiro-role.ini","zhang","123");
		Assert.assertTrue(subject.hasRole("role1"));
		Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1","role2")));
		boolean[] result = subject.hasRoles(Arrays.asList("role1","role2","role3"));
		Assert.assertTrue(result[0]);
		Assert.assertTrue(result[1]);
		Assert.assertFalse(result[2]);
		
		subject.checkRole("role1");
		//没有角色时抛出异常
//		org.apache.shiro.authz.UnauthorizedException: Subject does not have role [role3]
		subject.checkRoles("role1","role3");
	}
	
	@Test
	public void isPermitted(){
		Subject subject = login("classpath:c3/shiro-permission.ini", "zhang", "123");
		subject.checkPermission("user:create");
		subject.checkPermissions("user:update","user:delete");
		//没有权限，抛出异常
//		org.apache.shiro.authz.UnauthorizedException: Subject does not have permission [user:view]
		subject.checkPermission("user:view");
	}
}
