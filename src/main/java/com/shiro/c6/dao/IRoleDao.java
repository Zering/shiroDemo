package com.shiro.c6.dao;

import com.shiro.c6.entity.Role;

public interface IRoleDao {

	public Role createRole(Role role);
	public void deleteRole(Long roleId);
	
	public void correlationPermission(Long roleId, Long... permissionIds);
	public void uncorrelationPermission(Long roleId, Long... permissionIds);
}
