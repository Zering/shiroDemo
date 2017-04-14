package com.shiro.c6.dao;

import java.util.Set;

import com.shiro.c6.entity.User;

public interface IUserDao {

	public User createUser(User user);
	public User updateUser(User user);
	public void DeleteUser(Long userId);
	
	public void correlationRoles(Long userId,Long... roleIds);
	public void uncorrelationRoles(Long userId,Long... roleIds);
	
	public User findOne(Long userId);
	public User findUserByUsername(String username);
	
	public Set<String> findRoles(String username);
	public Set<String> findPermissions(String username);
}
