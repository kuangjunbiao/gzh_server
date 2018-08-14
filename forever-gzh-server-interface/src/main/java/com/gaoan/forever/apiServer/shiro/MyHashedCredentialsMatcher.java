package com.gaoan.forever.apiServer.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {

	@Override
	public void setHashAlgorithmName(String hashAlgorithmName) {
		super.setHashAlgorithmName(hashAlgorithmName);
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		return super.doCredentialsMatch(token, info);
	}

}
