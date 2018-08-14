package com.gaoan.forever.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbSalesOrderEntity;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.model.result.SalesOrderInfoModel;
import com.gaoan.forever.model.result.StatisticsInfoModel;

/**
 * 名称: TbSalesOrderMapper 描述: 出货Mapper接口 类型: JAVA
 */
public interface TbSalesOrderMapper extends BaseMapper<TbSalesOrderEntity> {

	List<SalesOrderInfoModel> querySalesOrder(OrderQueryConditionModel condition);

	SalesOrderInfoModel querySalesDetail(Long id);

	StatisticsInfoModel queryStatisticsInfo(@Param("type") int type, @Param("date") String date, @Param("userId") Long userId);

}