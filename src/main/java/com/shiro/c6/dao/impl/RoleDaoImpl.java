package com.shiro.c6.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.shiro.c6.dao.IRoleDao;
import com.shiro.c6.entity.Role;
import com.shiro.c6.util.JdbcTemplateUtil;

public class RoleDaoImpl implements IRoleDao {

	private JdbcTemplate jdbcTemplate = JdbcTemplateUtil.template();

	@Override
	public Role createRole(final Role role) {
		final String sql = "insert into sys_roles (role,description,available)values(?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement prepareStatement = con.prepareStatement(sql, new String[] { "id" });
				prepareStatement.setString(1, role.getRole());
				prepareStatement.setString(2, role.getDescription());
				prepareStatement.setBoolean(3, role.getAvailable());
				return prepareStatement;
			}
		}, keyHolder);
		role.setId(keyHolder.getKey().longValue());
		return role;
	}

	@Override
	public void deleteRole(Long roleId) {
		String sql = "delete from sys_users_roles where role_id = ?";
		jdbcTemplate.update(sql, roleId);
		sql = "delete from sys_roles where id = ?";
		jdbcTemplate.update(sql, roleId);
	}

	@Override
	public void correlationPermission(Long roleId, Long... permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "insert into sys_roles_permissions (role_id,permission_id) values(?,?)";
		for (Long permissionId : permissionIds) {
			if (!exists(roleId, permissionId))
				jdbcTemplate.update(sql, roleId, permissionId);
		}
	}

	@Override
	public void uncorrelationPermission(Long roleId, Long... permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			return;
		}
		String sql = "delete from sys_roles_permissions where role_id = ? and permission_id = ?";
		for (Long permissionId : permissionIds) {
			if (exists(roleId, permissionId))
				jdbcTemplate.update(sql, roleId, permissionId);
		}
	}

	private boolean exists(Long roleId, Long permissionId) {
		String sql = "select count(1) from sys_roles_permissions where role_id = ? and permission_id = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, roleId, permissionId) != 0;
	}

}
