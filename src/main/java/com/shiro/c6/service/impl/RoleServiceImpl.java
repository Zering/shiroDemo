package com.shiro.c6.service.impl;

import com.shiro.c6.dao.IRoleDao;
import com.shiro.c6.dao.impl.RoleDaoImpl;
import com.shiro.c6.entity.Role;
import com.shiro.c6.service.IRoleService;

public class RoleServiceImpl implements IRoleService {
	
	private IRoleDao roledao = new RoleDaoImpl();

	@Override
	public Role createRole(Role role) {
		return roledao.createRole(role);
	}

	@Override
	public void deleteRole(Long roleId) {
		roledao.deleteRole(roleId);
	}

	@Override
	public void correlationPermissions(Long roleId, Long... permissionIds) {
		roledao.correlationPermission(roleId, permissionIds);
	}

	@Override
	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
		roledao.uncorrelationPermission(roleId, permissionIds);
	}

}
