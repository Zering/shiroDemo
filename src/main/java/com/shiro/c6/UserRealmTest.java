package com.shiro.c6;

import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

public class UserRealmTest extends BaseTest {
	
	@Test
	public void testLoginSuccess(){
		System.out.println(user1);
		Subject subject = login("classpath:c6/shiro.ini", user1.getUsername(), password);
		Assert.assertTrue(subject.isAuthenticated());
	}
	
	@Test
    public void testHasRole() {
        Subject subject = login("classpath:c6/shiro.ini", user1.getUsername(), password );
        Assert.assertTrue(subject.hasRole("admin"));
    }
}
