package com.shiro.c5.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm2 extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = "liu"; // 用户名及salt1
		String salt2 = "1b889a90cb18e42a3cbf4b3ba8768e9b";
		String password = "059e6cc0cb8898950e0a275e7cbe401f"; // 加密后的密码
		SimpleAuthenticationInfo ai = new SimpleAuthenticationInfo(username, password, getName());
		ai.setCredentialsSalt(ByteSource.Util.bytes(username + salt2)); // 盐是用户名+随机数
		return ai;
	}

}
