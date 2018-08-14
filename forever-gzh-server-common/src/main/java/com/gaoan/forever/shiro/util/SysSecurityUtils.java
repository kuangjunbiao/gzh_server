package com.gaoan.forever.shiro.util;

import org.apache.shiro.subject.Subject;

import com.gaoan.forever.shiro.bean.ShiroUser;

public abstract class SysSecurityUtils {

	public static <T> T getLoginUser() {
		ShiroUser shiroUser = getShiroUser();
		if (null != shiroUser) {
			return (T) shiroUser.getUser();
		}
		return null;
	}

	public static ShiroUser getShiroUser() {
		Subject subject = getSubject();
		return (ShiroUser) subject.getPrincipal();
	}

	public static Subject getSubject() {
		return org.apache.shiro.SecurityUtils.getSubject();
	}
}