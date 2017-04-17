package com.shiro.c6;

import java.util.Set;

import org.junit.Test;

import com.shiro.c3.BaseTest;
import com.shiro.c6.entity.Permission;
import com.shiro.c6.entity.Role;
import com.shiro.c6.entity.User;
import com.shiro.c6.service.IPermissionService;
import com.shiro.c6.service.IRoleService;
import com.shiro.c6.service.IUserService;
import com.shiro.c6.service.impl.PermissionSerivceImpl;
import com.shiro.c6.service.impl.RoleServiceImpl;
import com.shiro.c6.service.impl.UserServiceImpl;

public class ServiceTest extends BaseTest {

	IUserService service = new UserServiceImpl();
	IPermissionService pService = new PermissionSerivceImpl();
	IRoleService rService = new RoleServiceImpl();
	@Test
	public void testCreateUser(){
		User user = new User("zhang", "123");
		User DBUserInfo = service.createUser(user);
		System.out.println(DBUserInfo.toString());
	}
	
	// 123 - 9ae84486a3c43a8a75189cb48d2b9f15
	// 456 - fb7a8f4fb53960eabcad6a6e216d1f55
	@Test
	public void testFindUserByUsername(){
		User user = service.findUserByUsername("zhang");
		System.out.println(user);
	}
	
	@Test
	public void testChangePassword(){
		User user = service.findUserByUsername("zhang");
		String newPassword = "456";
		service.changePassword(user.getId(), newPassword);
		//fb7a8f4fb53960eabcad6a6e216d1f55
	}
	
	@Test
	public void testCreatePermission(){
		Permission p = new Permission("test:create", "test module add", Boolean.TRUE);
		pService.createPermission(p);
	}
	
	@Test
	public void testCreateRoles(){
		Role r = new Role("test","测试组",Boolean.TRUE);
		rService.createRole(r);
	}
	
	
	
	@Test
	public void testFindRoles(){
		Set<String> findRoles = service.findRoles("zhang");
		if(findRoles != null){
			for (String role : findRoles) {
				System.out.println(role);
			}
		}
	}
}
