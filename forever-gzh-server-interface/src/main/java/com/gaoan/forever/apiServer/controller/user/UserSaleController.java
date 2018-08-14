package com.gaoan.forever.apiServer.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.constant.UserConstant;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.service.ITbUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/usersale")
@Api(value = "UserSaleController", description = "用户安全信息控制器")
public class UserSaleController {

	private static final Logger logger = LoggerFactory.getLogger(UserSaleController.class);

	@Autowired
	private ITbUserService tbUserService;

	@ApiOperation(value = "用户登录")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "map", value = "请求参数", paramType = "body", dataType = "String") })
	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object login(HttpServletRequest request, @RequestBody(required = true) Map<String, String> map)
			throws Exception {

		Message.Builder builder = Message.newBuilder();
		Subject subject = SecurityUtils.getSubject();
		logger.info("request sessionId = {} ", request.getSession().getId());
		logger.info("subject sessionId = {} ", subject.getSession().getId());

		if (map == null || map.isEmpty()) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		String userName = map.get("userName");
		String password = map.get("password");

		tbUserService.login(userName, password);

		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);

		try {
			subject.login(token);
		} catch (Exception e) {
			throw new AppException(MessageInfoConstant.ACCOUNT_OR_PASSWORD_IS_ERROR);
		}

		// 获取当前用户具有权限的菜单
		List<Map<String, Object>> list = tbUserService.queryUserResources(userName.trim());

		builder.put("list", list);
		builder.put("sessionId", subject.getSession().getId());
		return builder.builldJson();
	}

	@ApiOperation(value = "用户退出")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/loginout", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object loginout(HttpServletRequest request) throws Exception {

		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		subject.getSession().removeAttribute(UserConstant.SESSION_LOGIN_USER_KEY);

		Message.Builder builder = Message.newBuilder();
		return builder.builldJson();
	}

}
