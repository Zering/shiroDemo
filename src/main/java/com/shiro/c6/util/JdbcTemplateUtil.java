package com.shiro.c6.util;

import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcTemplateUtil {

	private static JdbcTemplate jdbcTemplate;

	public static JdbcTemplate template() {
		if (jdbcTemplate == null) {
			jdbcTemplate = createJdbcTemplate();
		}
		return jdbcTemplate;
	}

	private static JdbcTemplate createJdbcTemplate() {

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3300/shiro");
		dataSource.setUsername("root");
		dataSource.setPassword("1qaz2wsx");
		return new JdbcTemplate(dataSource);
	}
}
