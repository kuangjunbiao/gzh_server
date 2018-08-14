package com.gaoan.forever.apiServer.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;

import com.gaoan.forever.constant.UserConstant;
import com.gaoan.forever.entity.TbRolePermissionEntity;
import com.gaoan.forever.entity.TbUserEntity;
import com.gaoan.forever.service.ITbRolePermissionService;
import com.gaoan.forever.service.ITbUserService;
import com.gaoan.forever.shiro.util.SysSecurityUtils;

/**
 * 
 * @author three
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private ITbUserService tbUserService;

	@Autowired
	private ITbRolePermissionService tbRolePermissionService;

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		TbUserEntity user = (TbUserEntity) SecurityUtils.getSubject().getPrincipal();

		List<TbRolePermissionEntity> resourcesList = tbRolePermissionService.queryAll(null);

		// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// for (TbResourcesEntity resources : resourcesList) {
		// info.addStringPermission(resources.getResUrl());//
		// user:list;user:create
		// }
		return info;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// 获取用户的输入的账号.
		String userName = (String) token.getPrincipal();

		TbUserEntity queryEntity = new TbUserEntity();
		queryEntity.setUserName(userName);
		TbUserEntity userEntity = tbUserService.queryInfoByEntity(queryEntity);

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userEntity, userEntity.getPassword(),
				ByteSource.Util.bytes(userName), getName());

		setSession(UserConstant.SESSION_LOGIN_USER_KEY, userEntity);
		SysSecurityUtils.getSubject().getSession()
				.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, userName);
		return authenticationInfo;
	}

	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {

		SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		MyHashedCredentialsMatcher hashedCredentialsMatcher = new MyHashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		super.setCredentialsMatcher(hashedCredentialsMatcher);
	}

	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			// 设置超时时间ms 12小时
			session.setTimeout(12 * 60 * 60 * 1000);
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

}
