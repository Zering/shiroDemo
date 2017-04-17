package com.shiro.c6.dao;

import com.shiro.c6.entity.Permission;

public interface IPermissionDao {

	public Permission createPermission(Permission permission);
	public void deletePermission(Long permissionId);
}
