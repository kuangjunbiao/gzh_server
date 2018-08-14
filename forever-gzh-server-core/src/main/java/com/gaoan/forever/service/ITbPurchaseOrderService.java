package com.gaoan.forever.service;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbPurchaseOrderEntity;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.model.result.PurchaseOrderInfoModel;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbPurchaseOrderService 描述: 进货信息Service接口 类型: JAVA
 * 
 */
public interface ITbPurchaseOrderService extends IBaseService<TbPurchaseOrderEntity> {

	public PageInfo<PurchaseOrderInfoModel> queryPurchaseOrder(OrderQueryConditionModel condition, int page,
			int pageSize);

	public PurchaseOrderInfoModel queryPurchaseDetail(Long id);

	public void insertPurchaseOrderByHave(PurchaseOrderInfoModel model);

	public void insertPurchaseOrderByUnHave(PurchaseOrderInfoModel model);

	public void updatePurchaseOrder(PurchaseOrderInfoModel order);

	public void delPurchaseOrder(Long orderId);
}