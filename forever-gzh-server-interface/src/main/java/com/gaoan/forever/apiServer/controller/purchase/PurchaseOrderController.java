package com.gaoan.forever.apiServer.controller.purchase;

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
import com.gaoan.forever.model.Message;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.model.result.PurchaseOrderInfoModel;
import com.gaoan.forever.service.ITbPurchaseOrderService;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/purchase")
@Api(value = "PurchaseOrderController", description = "进货信息控制器")
public class PurchaseOrderController {

	@Autowired
	private ITbPurchaseOrderService tbPurchaseOrderService;

	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

	@ApiOperation(value = "获取进货列表")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "condition", value = "查询条件", paramType = "body", dataType = "json"),
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", dataType = "int", required = false),
			@ApiImplicitParam(name = "pageSize", value = "每页数据数", paramType = "query", dataType = "int", required = false) })
	@RequestMapping(value = "/getPurchaseOrderList", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object getPurchaseOrderList(HttpServletRequest request, @RequestBody OrderQueryConditionModel condition,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "15") int pageSize) throws Exception {

		PageInfo<PurchaseOrderInfoModel> pageInfo = tbPurchaseOrderService.queryPurchaseOrder(condition, page,
				pageSize);

		Message.Builder build = Message.newBuilder();
		build.put("pageInfo", pageInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "获取进货详情")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "orderId", value = "编号", paramType = "query", dataType = "Long", required = true) })
	@RequestMapping(value = "/getPurchaseOrderDetail", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getPurchaseOrderDetail(HttpServletRequest request, @RequestParam(required = true) Long orderId)
			throws Exception {

		PurchaseOrderInfoModel model = (PurchaseOrderInfoModel) tbPurchaseOrderService.queryPurchaseDetail(orderId);

		if (model == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		Message.Builder build = Message.newBuilder();
		build.put("info", model);

		return build.builldJson();
	}

	@ApiOperation(value = "新增进货信息(已有)")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "order", value = "新增对象", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/insertPurchaseOrderByHave", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object insertPurchaseOrderByHave(HttpServletRequest request, @RequestBody PurchaseOrderInfoModel order)
			throws Exception {

		tbPurchaseOrderService.insertPurchaseOrderByHave(order);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "新增进货信息(未有)")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "order", value = "新增对象", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/insertPurchaseOrderByUnHave", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object insertPurchaseOrderByUnHave(HttpServletRequest request, @RequestBody PurchaseOrderInfoModel order)
			throws Exception {

		tbPurchaseOrderService.insertPurchaseOrderByUnHave(order);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "修改出货信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "order", value = "修改对象", paramType = "body", dataType = "json") })
	@RequestMapping(value = "/updatePurchaseOrder", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.POST })
	@ResponseBody
	public Object updatePurchaseOrder(HttpServletRequest request, @RequestBody PurchaseOrderInfoModel order)
			throws Exception {

		tbPurchaseOrderService.updatePurchaseOrder(order);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}

	@ApiOperation(value = "删除出货信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "orderId", value = "出货单编号", paramType = "query", dataType = "Long", required = true) })
	@RequestMapping(value = "/delPurchaseOrder", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object delPurchaseOrder(HttpServletRequest request, @RequestParam(required = true) Long orderId)
			throws Exception {

		tbPurchaseOrderService.delPurchaseOrder(orderId);

		Message.Builder build = Message.newBuilder();
		return build.builldJson();
	}
}
