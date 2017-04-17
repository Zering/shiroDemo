package com.shiro.c6.service.impl;

import com.shiro.c6.dao.IPermissionDao;
import com.shiro.c6.dao.impl.PermissionDaoImpl;
import com.shiro.c6.entity.Permission;
import com.shiro.c6.service.IPermissionService;

public class PermissionSerivceImpl implements IPermissionService{

	private IPermissionDao permissionDao = new PermissionDaoImpl();
	@Override
	public Permission createPermission(Permission permission) {
		return permissionDao.createPermission(permission);
	}

	@Override
	public void deletePermission(Long permissionId) {
		permissionDao.deletePermission(permissionId);
	}

}
