package com.shiro.c6.service;

import com.shiro.c6.entity.Permission;

public interface IPermissionService {

	/**
	 * 创建权限
	 * 
	 * @param permission
	 * @return
	 */
	public Permission createPermission(Permission permission);

	/**
	 * 删除权限
	 * 
	 * @param permissionId
	 */
	public void deletePermission(Long permissionId);
}
