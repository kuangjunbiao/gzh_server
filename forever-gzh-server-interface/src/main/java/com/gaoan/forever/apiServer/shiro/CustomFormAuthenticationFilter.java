package com.gaoan.forever.apiServer.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				return executeLogin(request, response);
			} else {
				return true;
			}
		} else {
			// saveRequestAndRedirectToLogin(request, response);
			WebUtils.saveRequest(request);
			request.getRequestDispatcher("/api/user/unauthorizedLogin").forward(request, response);
			return false;
		}
	}
}
