package com.shiro.c4;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 纯java无配置
 * 
 * @author zhanghaojie
 *
 */
public class NoConfigurationTest {

	@Test
	public void jdbcTest(){
		//设置Authenticator
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		DefaultSecurityManager manager = new DefaultSecurityManager();
		manager.setAuthenticator(authenticator);
		//设置Authorizer
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		authorizer.setPermissionResolver(new WildcardPermissionResolver());
		//设置Realm
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3300/shiro");
		dataSource.setUsername("root");
		dataSource.setPassword("1qaz2wsx");
		
		JdbcRealm realm = new JdbcRealm();
		realm.setDataSource(dataSource);
		realm.setPermissionsLookupEnabled(true);
		manager.setRealms(Arrays.asList((Realm)realm));
		
		SecurityUtils.setSecurityManager(manager);
		
		Subject subject = SecurityUtils.getSubject();
		
		subject.login(new UsernamePasswordToken("zhang", "123"));
		Assert.assertTrue(subject.isAuthenticated());
		
	}
}
