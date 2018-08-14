package com.gaoan.forever.apiServer.controller.statistics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gaoan.forever.model.Message;
import com.gaoan.forever.model.result.StatisticsInfoModel;
import com.gaoan.forever.service.ITbConsumeService;
import com.gaoan.forever.service.ITbSalesOrderService;
import com.gaoan.forever.util.CsvUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/statistics")
@Api(value = "StatisticsController", description = "统计控制器")
public class StatisticsController {

	@Autowired
	private ITbSalesOrderService tbSalesOrderService;

	@Autowired
	private ITbConsumeService tbConsumeService;

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	@ApiOperation(value = "获取统计信息")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "统计类型,1:日,2:月,3:年", paramType = "query", dataType = "int", required = true),
			@ApiImplicitParam(name = "date", value = "统计时间(日,月,年)", paramType = "query", dataType = "String", required = true),
			@ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query", dataType = "Long", required = true) })
	@RequestMapping(value = "/getStatisticsInfo", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object getStatisticsInfo(HttpServletRequest request, @RequestParam(required = true) int type,
			@RequestParam(required = true) String date, @RequestParam(required = false) Long userId) throws Exception {
		logger.info("");

		StatisticsInfoModel statisticsInfo = tbSalesOrderService.queryStatisticsInfo(type, date, userId);

		Message.Builder build = Message.newBuilder();
		build.put("info", statisticsInfo);

		return build.builldJson();
	}

	@ApiOperation(value = "导出出货详细")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "统计类型,1:日,2:月,3:年", paramType = "query", dataType = "int", required = true),
			@ApiImplicitParam(name = "date", value = "统计时间(日,月,年)", paramType = "query", dataType = "String", required = true),
			@ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query", dataType = "Long", required = true) })
	@RequestMapping(value = "/exportSalesList", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object exportSalesList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) int type, @RequestParam(required = true) String date,
			@RequestParam(required = false) Long userId) throws Exception {
		logger.info("");

		String[] cols = { "createTime", "purchaseOrderName", "goodsName", "colorName", "sizeName", "sellPrice",
				"quantity", "avgPrice", "profit", "realName" };

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("date", date);
		map.put("userId", userId);

		CsvUtil.exportCsvFile(request, response, Arrays.asList(cols), tbSalesOrderService, map, "销售明细", true);

		return null;
	}

	@ApiOperation(value = "导出支出明细")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "type", value = "统计类型,1:日,2:月,3:年", paramType = "query", dataType = "int", required = true),
			@ApiImplicitParam(name = "date", value = "统计时间(日,月,年)", paramType = "query", dataType = "String", required = true) })
	@RequestMapping(value = "/exportConsumeList", produces = "application/json;charset=UTF-8", method = {
			RequestMethod.GET })
	@ResponseBody
	public Object exportConsumeList(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) int type, @RequestParam(required = true) String date) throws Exception {
		logger.info("");

		String[] cols = { "date", "amount", "remark" };

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("date", date);

		CsvUtil.exportCsvFile(request, response, Arrays.asList(cols), tbConsumeService, map, "支出明细", true);

		return null;
	}

}
