package com.gaoan.forever.service;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbSalesOrderEntity;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.model.result.SalesOrderInfoModel;
import com.gaoan.forever.model.result.StatisticsInfoModel;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbSalesOrderService 描述: 出货信息Service接口 类型: JAVA
 * 
 */
public interface ITbSalesOrderService extends IBaseService<TbSalesOrderEntity> {

	PageInfo<SalesOrderInfoModel> querySalesOrder(OrderQueryConditionModel condition, int page, int pageSize);

	SalesOrderInfoModel querySalesDetail(Long id);

	void insertSalesOrder(SalesOrderInfoModel order);

	void updateSalesOrder(SalesOrderInfoModel order);

	void delSalesOrder(Long orderId);

	StatisticsInfoModel queryStatisticsInfo(int type, String date, Long userId);
}