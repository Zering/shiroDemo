package com.shiro.c6.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.shiro.c6.entity.User;

public class PasswordHelper {

	private RandomNumberGenerator generator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private final int hashIterations = 2;

	public void encryptPassword(User user) {
		user.setSalt(generator.nextBytes().toHex());
		String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
		user.setPassword(newPassword);
	}
}
