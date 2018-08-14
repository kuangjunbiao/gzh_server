package com.gaoan.forever.service;

import java.util.List;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbStockEntity;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbStockService 描述: 库存信息Service接口 类型: JAVA
 * 
 */
public interface ITbStockService extends IBaseService<TbStockEntity> {

	public PageInfo<TbStockEntity> getStockPageInfo(OrderQueryConditionModel conditionModel, int page, int pageSize);

	public List<String> queryPurchaseNameList();

	public List<String> queryGoodsList(String purchaseOrderName);

}