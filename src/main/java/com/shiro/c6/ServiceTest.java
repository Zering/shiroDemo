package com.shiro.c6;

import org.junit.Test;

import com.shiro.c3.BaseTest;
import com.shiro.c6.entity.User;
import com.shiro.c6.service.IUserService;
import com.shiro.c6.service.impl.UserServiceImpl;

public class ServiceTest extends BaseTest {

	@Test
	public void testCreateUser(){
		User user = new User("zhang", "123");
		IUserService service = new UserServiceImpl();
		User DBUserInfo = service.createUser(user);
		System.out.println(DBUserInfo.toString());
	}
}
