package com.gaoan.forever.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbStockEntity;
import com.gaoan.forever.model.query.OrderQueryConditionModel;

/**
 * 名称: TbStockMapper 描述: 库存Mapper接口 类型: JAVA
 */
public interface TbStockMapper extends BaseMapper<TbStockEntity> {

	public List<TbStockEntity> queryStockList(OrderQueryConditionModel conditionModel);

	public List<String> queryPurchaseNameList();

	public List<String> queryGoodsList(@Param("purchaseOrderName") String purchaseOrderName);

	public int subtractQty(@Param("id") Long id, @Param("qty") Long qty);

	public int addQty(@Param("id") Long id, @Param("qty") Long qty);

}