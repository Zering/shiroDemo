package com.shiro.c6.service.impl;

import java.util.Set;

import com.shiro.c6.dao.IUserDao;
import com.shiro.c6.dao.impl.UserDaoImpl;
import com.shiro.c6.entity.User;
import com.shiro.c6.service.IUserService;
import com.shiro.c6.util.PasswordHelper;

public class UserServiceImpl implements IUserService{

	//实际引用中会使用注入的方式
	PasswordHelper passwordHelper = new PasswordHelper();
	IUserDao userDao = new UserDaoImpl();
	
	@Override
	public User createUser(User user) {
		//加密密码
		passwordHelper.encryptPassword(user);
		return userDao.createUser(user);
	}

	@Override
	public void changePassword(Long userId, String newPassword) {
		User user = userDao.findOne(userId);
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		System.out.println(user.getPassword());
		userDao.updateUser(user);
	}

	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}

	@Override
	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
