package com.gaoan.forever.apiServer.controller.size;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbSizeEntity;
import com.gaoan.forever.model.Message;
import com.gaoan.forever.service.ITbSizeService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/size")
@Api(value = "SizeController", description = "尺寸信息控制器")
public class SizeController {

	@Autowired
	private ITbSizeService tbSizeService;

	// private static final Logger logger =
	// LoggerFactory.getLogger(SizeController.class);

	@ApiOperation(value = "尺寸资源列表")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数据数", paramType = "query", dataType = "int", required = false) })
	@RequestMapping(value = "/getSizePage", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object getSizePage(HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int pageSize) throws Exception {
		PageInfo<TbSizeEntity> pageInfo = tbSizeService.getPageInfo(page, pageSize);

		Message.Builder build = Message.newBuilder();
		build.put("pageInfo", pageInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "获取尺寸列表,不分页")
	@ApiImplicitParams(value = {})
	@RequestMapping(value = "/getSizeList", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object getSizeList(HttpServletRequest request) throws Exception {
		List<TbSizeEntity> list = tbSizeService.queryAll(null);

		Message.Builder build = Message.newBuilder();
		build.put("list", list);

		return build.builldJson();
	}

	@ApiOperation(value = "新增尺寸")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "sizeName", value = "尺寸名称", paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "sizeCode", value = "尺寸简称", paramType = "query", dataType = "String") })
	@RequestMapping(value = "/insertSizeInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object insertSizeInfo(HttpServletRequest request, @RequestParam(required = true) String sizeName,
			@RequestParam(required = true) String sizeCode) throws Exception {

		tbSizeService.insertSizeInfo(sizeName, sizeCode);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "删除尺寸")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "sizeId", value = "尺寸编号", paramType = "query", dataType = "Long") })
	@RequestMapping(value = "/delSize", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Object delSize(HttpServletRequest request, @RequestParam(required = true) Long sizeId) throws Exception {

		int result = tbSizeService.deleteByPrimaryKey(sizeId);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

}
