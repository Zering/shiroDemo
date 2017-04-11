package com.shiro.c2.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
/**
 * 多realm配置
 * 
 * @author zhanghaojie
 *
 */
public class UsernamePasswordRealm2 implements Realm {

	public String getName() {
		return "username & password Realm 2";
	}

	public boolean supports(AuthenticationToken token) {
		//仅支持帐号密码登录
		return token instanceof UsernamePasswordToken;
	}

	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[])token.getCredentials());
		// 帐号密码验证
		if(!"wang".equals(username)){
			throw new UnknownAccountException();
		}
		if(!"123".equals(password)){
			throw new IncorrectCredentialsException();
		}
		
		//验证通过 输出信息
		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
