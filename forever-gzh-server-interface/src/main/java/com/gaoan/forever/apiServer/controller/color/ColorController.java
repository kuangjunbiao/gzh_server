package com.gaoan.forever.apiServer.controller.color;

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
import com.gaoan.forever.entity.TbColorEntity;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.service.ITbColorService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/color")
@Api(value = "ColorController", description = "颜色控制器")
public class ColorController {

	@Autowired
	private ITbColorService tbColorService;

	private static final Logger logger = LoggerFactory.getLogger(ColorController.class);

	@ApiOperation(value = "获取颜色列表")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数据数", paramType = "query", dataType = "int", required = false) })
	@RequestMapping(value = "/getColorPage", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object getColorPage(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int pageSize) throws Exception {
		PageInfo<TbColorEntity> pageInfo = tbColorService.getColorPage(page, pageSize);

		Message.Builder build = Message.newBuilder();
		build.put("pageInfo", pageInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "获取颜色列表,不分页")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/getColorList", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getColorList(HttpServletRequest request) throws Exception {
		List<TbColorEntity> list = tbColorService.queryAll(null);

		Message.Builder build = Message.newBuilder();
		build.put("list", list);

		return build.builldJson();
	}

	@ApiOperation(value = "新增颜色")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "colorName", value = "颜色名称", paramType = "query", dataType = "String") })
	@RequestMapping(value = "/insertColorInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object insertColorInfo(HttpServletRequest request, @RequestParam(required = true) String colorName)
			throws Exception {

		tbColorService.insertColorInfo(colorName);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "删除颜色")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "colorId", value = "颜色编号", paramType = "query", dataType = "Long") })
	@RequestMapping(value = "/delColor", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object delColor(HttpServletRequest request, @RequestParam(required = true) Long colorId) throws Exception {

		int result = tbColorService.deleteByPrimaryKey(colorId);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

}
