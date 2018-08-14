package com.gaoan.forever.apiServer.controller.consume;

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

import com.gaoan.forever.entity.TbConsumeEntity;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.service.ITbConsumeService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/consume")
@Api(value = "ConsumeController", description = "消费信息控制器")
public class ConsumeController {

	@Autowired
	private ITbConsumeService tbConsumeService;

	private static final Logger logger = LoggerFactory.getLogger(ConsumeController.class);

	@ApiOperation(value = "获取消费列表")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "param", value = "日期", paramType = "body", dataType = "json", required = false),
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数据数", paramType = "query", dataType = "int", required = false) })
	@RequestMapping(value = "/getConsumePageInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object getConsumePageInfo(HttpServletRequest request,
			@RequestBody(required = false) Map<String, String> param,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int pageSize) throws Exception {

		PageInfo<TbConsumeEntity> pageInfo = tbConsumeService.getConsumePageInfo(param, page, pageSize);

		Message.Builder build = Message.newBuilder();
		build.put("pageInfo", pageInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "获取消费详情")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "编号", paramType = "query", dataType = "Long", required = true) })
	@RequestMapping(value = "/getConsumeDetail", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getConsumeDetail(HttpServletRequest request, @RequestParam(required = true) Long id)
			throws Exception {

		TbConsumeEntity entity = (TbConsumeEntity) tbConsumeService.getConsumeDetail(id);

		Message.Builder build = Message.newBuilder();
		build.put("info", entity);

		return build.builldJson();
	}

	@ApiOperation(value = "新增消費信息")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/insertConsumeInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object insertConsumeInfo(HttpServletRequest request, @RequestBody TbConsumeEntity entity) throws Exception {

		tbConsumeService.insertConsumeInfo(entity);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "修改消費信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "entity", value = "修改对象", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/updateConsumeInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object updateConsumeInfo(HttpServletRequest request, @RequestBody TbConsumeEntity entity) throws Exception {

		tbConsumeService.updateConsumeInfo(entity);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "删除消費信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "id", value = "消费编号", paramType = "query", dataType = "Long", required = true) })
	@RequestMapping(value = "/deleteConsumeInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object deleteConsumeInfo(HttpServletRequest request, @RequestParam(required = true) Long id)
			throws Exception {

		tbConsumeService.deleteConsumeInfo(id);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}
}
