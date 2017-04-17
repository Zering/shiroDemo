package com.shiro.c6.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.shiro.c6.dao.IPermissionDao;
import com.shiro.c6.entity.Permission;
import com.shiro.c6.util.JdbcTemplateUtil;

public class PermissionDaoImpl implements IPermissionDao {
	private JdbcTemplate jdbcTemplate = JdbcTemplateUtil.template(); 
	
	@Override
	public Permission createPermission(final Permission permission) {
		final String sql = "insert into sys_permissions (permission,description,available) values (?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
				ps.setString(1, permission.getPermission());
				ps.setString(2, permission.getDecription());
				ps.setBoolean(3, permission.getAvailable());
				return ps;
			}
		},keyHolder);
		permission.setId(keyHolder.getKey().longValue());
		return permission;
	}

	@Override
	public void deletePermission(Long permissionId) {
		String sql = "delete from sys_roles_permissions where permission_id = ?";
		jdbcTemplate.update(sql, permissionId);
		sql = "delete form sys_permissions where id = ?";
		jdbcTemplate.update(sql, permissionId);
	}

}
