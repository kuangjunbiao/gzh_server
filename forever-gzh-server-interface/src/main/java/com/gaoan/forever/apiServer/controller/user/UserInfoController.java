package com.gaoan.forever.apiServer.controller.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.constant.LanguageConstant;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbUserEntity;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.service.ITbUserService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user")
@Api(value = "UserInfoController", description = "用户信息控制器")
public class UserInfoController {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

	@Autowired
	private ITbUserService tbUserService;

	@ApiOperation(value = "获取用户列表")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数据数", paramType = "query", dataType = "int", required = false) })
	@RequestMapping(value = "/getUserPageInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object getUserPageInfo(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int pageSize) throws Exception {
		PageInfo<Map<String, Object>> pageInfo = tbUserService.getUserPageInfo(page, pageSize);

		Message.Builder build = Message.newBuilder();
		build.put("pageInfo", pageInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "获取用户列表,不分页")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/getUserList", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getUserList(HttpServletRequest request) throws Exception {
		List<TbUserEntity> list = tbUserService.queryAll(null);

		Message.Builder build = Message.newBuilder();
		build.put("list", list);

		return build.builldJson();
	}

	@ApiOperation(value = "获取用户详细信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "userId", value = "用户编号", paramType = "query", dataType = "String") })
	@RequestMapping(value = "/getUserDetail", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getUserDetail(HttpServletRequest request, @RequestParam(required = true) String userId)
			throws Exception {
		Map<String, Object> info = tbUserService.queryUserDetail(Long.parseLong(userId));

		Message.Builder build = Message.newBuilder();
		build.put("info", info);

		return build.builldJson();
	}

	@ApiOperation(value = "新增用户")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "map", value = "请求参数", paramType = "body", dataType = "String") })
	@RequestMapping(value = "/insertUser", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object insertUser(HttpServletRequest request, @RequestBody(required = true) Map<String, String> map)
			throws Exception {

		tbUserService.insertUser(map);

		Message.Builder builder = Message.newBuilder();

		return builder.builldJson();
	}

	@ApiOperation(value = "修改用户详细信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "map", value = "请求参数", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/updateUserInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object updateUserInfo(HttpServletRequest request, @RequestBody(required = true) Map<String, Object> map)
			throws Exception {

		if (map == null || map.isEmpty()) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		tbUserService.updateUserInfo(map);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "重置密码")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "userId", value = "用户ID", paramType = "query", dataType = "Long") })
	@RequestMapping(value = "/resetPass", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object resetPass(HttpServletRequest request, @RequestParam(required = true) Long userId) throws Exception {

		tbUserService.resetPass(userId);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "删除用户信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "userId", value = "用户编号", paramType = "query", dataType = "String") })
	@RequestMapping(value = "/delUserInfo", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object delUserInfo(HttpServletRequest request, @RequestParam(required = true) String userId)
			throws Exception {

		Long user_id = Long.parseLong(userId);

		// 删除用户信息
		tbUserService.delUserInfo(user_id);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "忘记密码")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "map", value = "请求对象", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/forgetPass", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object forgetPass(HttpServletRequest request, @RequestBody(required = true) Map<String, String> param)
			throws Exception {

		tbUserService.forgetPass(param);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "前端-未授权登录")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/unauthorizedLogin", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void loginGet(HttpServletRequest request) throws Exception {
		throw new AppException(MessageInfoConstant.USER_HAD_NOT_LOGIN);
	}

	@ApiOperation(value = "前端-测试")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "ID", paramType = "query", dataType = "long", required = true) })
	@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object test(HttpServletRequest request) throws Exception {
		logger.info(LanguageConstant.aurora_recharge_remark.getMsg());
		request.getSession().setAttribute("test", "id");
		Message.Builder builder = Message.newBuilder();
		return builder.builldJson();
	}

}
