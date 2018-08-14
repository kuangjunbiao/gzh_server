package com.gaoan.forever.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbPurchaseOrderEntity;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.model.result.PurchaseOrderInfoModel;

/**
 * 名称: TbPurchaseOrderMapper 描述: 进货Mapper接口 类型: JAVA
 */
public interface TbPurchaseOrderMapper extends BaseMapper<TbPurchaseOrderEntity> {

	List<PurchaseOrderInfoModel> queryPurchaseOrder(OrderQueryConditionModel condition);

	PurchaseOrderInfoModel queryPurchaseDetail(Long id);

	Map<String, Object> quertCurrTotal(Long id);

	Map<String, Object> quertTotalExcludeCurrOrder(@Param("id") Long id, @Param("orderId") Long orderId);
}