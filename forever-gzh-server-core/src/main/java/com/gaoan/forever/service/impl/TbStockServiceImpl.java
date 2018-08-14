package com.gaoan.forever.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.entity.TbStockEntity;
import com.gaoan.forever.mapper.TbStockMapper;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.service.ITbStockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbStockServiceImpl 描述: 库存处理类 类型: JAVA
 * 
 */
@Service("TbStockServiceImpl")
public class TbStockServiceImpl extends BaseService<TbStockEntity> implements ITbStockService {

	private final Logger logger = LoggerFactory.getLogger(TbStockServiceImpl.class);

	@Autowired
	private TbStockMapper tbStockMapper;

	/**
	 * 获取库存分页信息
	 */
	@Override
	public PageInfo<TbStockEntity> getStockPageInfo(OrderQueryConditionModel conditionModel, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<TbStockEntity> list = tbStockMapper.queryStockList(conditionModel);
		return new PageInfo<TbStockEntity>(list);
	}

	@Override
	public List<String> queryPurchaseNameList() {
		List<String> list = tbStockMapper.queryPurchaseNameList();
		return list;
	}

	@Override
	public List<String> queryGoodsList(String purchaseOrderName) {
		List<String> list = tbStockMapper.queryGoodsList(purchaseOrderName);
		return list;
	}

}