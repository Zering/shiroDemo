package com.shiro.c6.service;

import java.util.Set;

import com.shiro.c6.entity.User;

public interface IUserService {

	/**
	 * 创建用户
	 * 
	 * @param user
	 * @return
	 */
	public User createUser(User user);
	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(Long userId,String newPassword);
	/**
	 * 给用户添加角色
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void correlationRoles(Long userId,Long... roleIds);
	
	/**
	 * 移除用户角色
	 * 
	 * @param userId
	 * @param roleIds
	 */
	public void uncorrelationRoles(Long userId,Long... roleIds);
	/**
	 * 根据用户名,查找用户信息
	 * 
	 * @param username
	 * @return
	 */
	public User findUserByUsername(String username);
	/**
	 * 查看角色信息
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String username);
	/**
	 * 查看用户角色
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String username);
}
