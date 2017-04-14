package com.shiro.c6.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

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
				preparedStatement.setBoolean(4, user.getIslocked());
				return preparedStatement;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());
		return user;
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DeleteUser(Long userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void correlationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uncorrelationRoles(Long userId, Long... roleIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findOne(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findRoles(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> findPermissions(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
