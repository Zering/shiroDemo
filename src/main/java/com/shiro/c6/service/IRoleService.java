package com.shiro.c6.service;

import com.shiro.c6.entity.Role;

public interface IRoleService {

	/**
	 * 创建角色
	 * 
	 * @param role
	 * @return
	 */
	public Role createRole(Role role);

	/**
	 * 删除角色
	 * 
	 * @param roleId
	 */
	public void deleteRole(Long roleId);

	/**
	 * 添加角色-权限关系
	 * 
	 * @param roleId
	 * @param permissionIds
	 */
	public void correlationPermissions(Long roleId, Long... permissionIds);

	/**
	 * 移除角色-权限关系
	 * 
	 * @param roleId
	 * @param permissionIds
	 */
	public void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
