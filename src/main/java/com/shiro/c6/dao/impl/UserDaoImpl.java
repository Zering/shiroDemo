package com.shiro.c6.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.shiro.c6.dao.IUserDao;
import com.shiro.c6.entity.User;
import com.shiro.c6.util.JdbcTemplateUtil;

public class UserDaoImpl implements IUserDao {

	private JdbcTemplate jdbcTemplate = JdbcTemplateUtil.template();

	@Override
	public User createUser(final User user) {
		final String sql = "insert into sys_users(username, password, salt, locked) values(?,?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(sql, new String[] { "id" });
				preparedStatement.setString(1, user.getUsername());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.setString(3, user.getSalt());
				preparedStatement.setBoolean(4, user.getLocked());
				return preparedStatement;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	@Override
	public void updateUser(User user) {
		String sql = "update sys_users set username=?,password=?,salt=?,locked=? where id = ?";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getSalt(), user.getLocked(),
				user.getId());
	}

	@Override
	public void DeleteUser(Long userId) {
		String sql = "delelte from sys_users where id = ?";
		jdbcTemplate.update(sql, userId);
	}

	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		if (roleIds == null || roleIds.length == 0) {
			return;
		}
		String sql = "insert into sys_users_roles (user_id,role_id) values (?,?)";
		for (Long roleId : roleIds) {
			if(!exists(userId, roleId))
				jdbcTemplate.update(sql, userId, roleId);
		}
	}

	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		String sql = "delete from sys_users_roles where user_id = ? and role_id = ?";
		for (Long roleId : roleIds) {
			if (exists(userId, roleId)) {
				jdbcTemplate.update(sql, userId, roleId);
			}
		}
	}

	private boolean exists(Long userId, Long roleId) {
		String sql = "select count(1) from sys_users_roles where user_id = ? and role_id = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId, roleId) != 0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public User findOne(Long userId) {
		String sql = "select id,username,password,salt,locked from sys_users where id = ?";
		List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), userId);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public User findUserByUsername(String username) {
		String sql = "select id,username,password,salt,locked from sys_users where username = ?";
		List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), username);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<String> findRoles(String username) {
		String sql = "select role from sys_users u , sys_users_roles ur, sys_roles r where u.id = ur.user_id and ur.role_id = r.id and u.username = ?";
		return new HashSet(jdbcTemplate.queryForList(sql, String.class,username));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set<String> findPermissions(String username) {
		String sql = "SELECT permission FROM sys_users u , sys_users_roles ur, sys_roles r,sys_roles_permissions rp, sys_permissions p "
				+ "WHERE u.id = ur.user_id AND ur.role_id = r.id AND r.id = rp.role_id AND rp.permission_id = p.id AND u.username = ?";
		return new HashSet(jdbcTemplate.queryForList(sql, String.class, username));
	}

}
