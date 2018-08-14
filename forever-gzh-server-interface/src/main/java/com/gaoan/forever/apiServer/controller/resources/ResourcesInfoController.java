package com.gaoan.forever.apiServer.controller.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbResourcesEntity;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.service.ITbResourcesService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/resources")
@Api(value = "ResourcesInfoController", description = "资源信息控制器")
public class ResourcesInfoController {

	@Autowired
	private ITbResourcesService tbResourcesService;

	private static final Logger logger = LoggerFactory.getLogger(ResourcesInfoController.class);

	@ApiOperation(value = "获取资源列表")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数据数", paramType = "query", dataType = "int", required = false) })
	@RequestMapping(value = "/getResourcePage", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object getResourcePage(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int pageSize) throws Exception {
		PageInfo<TbResourcesEntity> pageInfo = tbResourcesService.getResourcesPageInfo(page, pageSize);

		Message.Builder build = Message.newBuilder();
		build.put("pageInfo", pageInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "获取资源列表,不分页")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/getResourceList", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getResourceList(HttpServletRequest request) throws Exception {
		List<TbResourcesEntity> list = tbResourcesService.queryAll(null);

		Message.Builder build = Message.newBuilder();
		build.put("list", list);

		return build.builldJson();
	}

	@ApiOperation(value = "获取资源详情")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "resourceId", value = "资源编号", paramType = "query", dataType = "Long") })
	@RequestMapping(value = "/getResourceDetail", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getResourceDetail(HttpServletRequest request, @RequestParam(required = true) Long resourceId)
			throws Exception {
		if (resourceId == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		TbResourcesEntity resourceInfo = tbResourcesService.getResourcesDetail(resourceId);

		Message.Builder build = Message.newBuilder();
		build.put("info", resourceInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "新增资源")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "resourcesName", value = "资源名称", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型", paramType = "query", dataType = "int") })
	@RequestMapping(value = "/insertResourcesInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object insertResourcesInfo(HttpServletRequest request, @RequestParam(required = true) String resourcesName,
			@RequestParam(required = true) Integer type) throws Exception {

		tbResourcesService.insertResources(resourcesName, type);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "删除资源")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "resourcesId", value = "资源编号", paramType = "query", dataType = "Long") })
	@RequestMapping(value = "/delResources", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object delResources(HttpServletRequest request, @RequestParam(required = true) Long resourcesId)
			throws Exception {

		tbResourcesService.delResources(resourcesId);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "修改资源")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "resourceId", value = "资源编号", paramType = "query", dataType = "Long"),
			@ApiImplicitParam(name = "resourceName", value = "资源名称", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "type", value = "类型", paramType = "query", dataType = "int") })
	@RequestMapping(value = "/updateResources", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object updateResources(HttpServletRequest request, @RequestParam(required = true) Long resourceId,
			@RequestParam(required = true) String resourceName, @RequestParam(required = true) Integer type)
					throws Exception {

		tbResourcesService.updateResources(resourceId, resourceName, type);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

}
