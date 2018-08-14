package com.gaoan.forever.apiServer.controller.role;

import java.util.List;

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
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbRoleEntity;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.service.ITbRoleService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/role")
@Api(value = "RoleInfoController", description = "角色信息控制器")
public class RoleInfoController {

	@Autowired
	private ITbRoleService tbRoleService;

	private static final Logger logger = LoggerFactory.getLogger(RoleInfoController.class);

	@ApiOperation(value = "获取角色列表")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数据数", paramType = "query", dataType = "int", required = false) })
	@RequestMapping(value = "/getRolePage", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object getRolePage(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int pageSize) throws Exception {

		PageInfo<TbRoleEntity> pageInfo = tbRoleService.getRolePageInfo(page, pageSize);

		Message.Builder build = Message.newBuilder();
		build.put("pageInfo", pageInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "获取角色列表,不分页")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/getRoleList", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getRoleList(HttpServletRequest request) throws Exception {

		List<TbRoleEntity> list = tbRoleService.queryAll(null);

		Message.Builder build = Message.newBuilder();
		build.put("list", list);

		return build.builldJson();
	}

	@ApiOperation(value = "获取角色详情")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "roleId", value = "角色编号", paramType = "query", dataType = "String") })
	@RequestMapping(value = "/getRoleDetail", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getRoleDetail(HttpServletRequest request, @RequestParam(required = true) String roleId)
			throws Exception {
		Long role_id = Long.parseLong(roleId);
		TbRoleEntity roleEntity = tbRoleService.queryByPrimaryKey(role_id);

		if (roleEntity == null) {
			logger.error("roleId = {}, 不存在.", role_id);
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		List<Long> list = tbRoleService.queryRolePermission(role_id);

		Message.Builder build = Message.newBuilder();
		build.put("info", roleEntity);
		build.put("permissionList", list);

		return build.builldJson();
	}

	@ApiOperation(value = "新增角色")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "resourceIdList", value = "资源id列表", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/insertRole", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object insertRole(HttpServletRequest request, @RequestParam(required = true) String roleName,
			@RequestBody(required = false) List<Long> resourceIdList) throws Exception {

		tbRoleService.insertRole(roleName, resourceIdList);
		Message.Builder build = Message.newBuilder();

		return build.builldJson();
	}

	@ApiOperation(value = "修改角色")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "roleId", value = "角色编号", paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = "roleName", value = "角色名称", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "resourcesIdList", value = "资源列表", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/updateRole", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Object updateRole(HttpServletRequest request, @RequestParam(required = true) Long roleId,
			@RequestParam(required = true) String roleName, @RequestBody(required = false) List<Long> resourcesIdList)
					throws Exception {
		tbRoleService.updateRole(roleId, roleName, resourcesIdList);
		Message.Builder build = Message.newBuilder();

		return build.builldJson();
	}

	@ApiOperation(value = "删除角色")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "roleId", value = "角色编号", paramType = "query", dataType = "Long") })
	@RequestMapping(value = "/delRole", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object delRole(HttpServletRequest request, @RequestParam(required = true) Long roleId) throws Exception {

		tbRoleService.delRole(roleId);
		Message.Builder build = Message.newBuilder();

		return build.builldJson();
	}

}
