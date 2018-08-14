package com.gaoan.forever.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.constant.UserConstant;
import com.gaoan.forever.entity.TbSalesOrderEntity;
import com.gaoan.forever.entity.TbStockEntity;
import com.gaoan.forever.entity.TbUserEntity;
import com.gaoan.forever.mapper.TbConsumeMapper;
import com.gaoan.forever.mapper.TbSalesOrderMapper;
import com.gaoan.forever.mapper.TbStockMapper;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.model.result.SalesOrderInfoModel;
import com.gaoan.forever.model.result.StatisticsInfoModel;
import com.gaoan.forever.service.ITbSalesOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbSalesOrderServiceImpl 描述: 出货信息处理类 类型: JAVA
 * 
 */
@Service("TbSalesOrderServiceImpl")
public class TbSalesOrderServiceImpl extends BaseService<TbSalesOrderEntity> implements ITbSalesOrderService {

	private final Logger logger = LoggerFactory.getLogger(TbSalesOrderServiceImpl.class);

	@Autowired
	private TbStockMapper tbStockMapper;

	@Autowired
	private TbSalesOrderMapper tbSalesOrderMapper;

	@Autowired
	private TbConsumeMapper tbConsumeMapper;

	@Override
	public PageInfo<SalesOrderInfoModel> querySalesOrder(OrderQueryConditionModel condition, int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<SalesOrderInfoModel> list = tbSalesOrderMapper.querySalesOrder(condition);
		return new PageInfo<>(list);
	}

	@Override
	public SalesOrderInfoModel querySalesDetail(Long id) {
		SalesOrderInfoModel model = tbSalesOrderMapper.querySalesDetail(id);
		return model;
	}

	@Transactional
	@Override
	public void insertSalesOrder(SalesOrderInfoModel order) {
		if (order == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}
		String purchaseOrderName = order.getPurchaseOrderName();
		String goodsName = order.getGoodsName();
		Long colorId = order.getColorId();
		Long sizeId = order.getSizeId();
		BigDecimal sellPrice = order.getSellPrice();
		Long quantity = order.getQuantity();
		Integer payType = order.getPayType();
		BigDecimal tagPrice = order.getTagPrice();
		String remark = order.getRemark();

		if (StringUtils.isEmpty(purchaseOrderName) || StringUtils.isEmpty(goodsName) || colorId == null
				|| sizeId == null || sellPrice == null || quantity == null || payType == null || tagPrice == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}
		purchaseOrderName = purchaseOrderName.trim();
		goodsName = goodsName.trim();

		if (sellPrice.compareTo(BigDecimal.ZERO) <= 0 || quantity < 0) {
			throw new AppException(MessageInfoConstant.DATAS_INFO_IS_NOT_EXIST);
		}

		// 修改库存
		TbStockEntity queryEntity = new TbStockEntity();
		queryEntity.setPurchaseOrderName(purchaseOrderName);
		queryEntity.setGoodsName(goodsName);
		queryEntity.setColorId(colorId);
		queryEntity.setSizeId(sizeId);
		TbStockEntity stockEntity = tbStockMapper.queryInfoByEntity(queryEntity);
		if (stockEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		int result = tbStockMapper.subtractQty(stockEntity.getId(), quantity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
		}

		BigDecimal quantity_bd = new BigDecimal(quantity);
		// 算出总价
		BigDecimal total = sellPrice.multiply(quantity_bd);

		TbUserEntity userEntity = (TbUserEntity) SecurityUtils.getSubject().getSession()
				.getAttribute(UserConstant.SESSION_LOGIN_USER_KEY);

		if (userEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbSalesOrderEntity salesOrderEntity = new TbSalesOrderEntity();
		salesOrderEntity.setGoodsId(stockEntity.getId());
		salesOrderEntity.setColorId(colorId);
		salesOrderEntity.setSizeId(sizeId);
		salesOrderEntity.setQuantity(quantity);
		salesOrderEntity.setSellPrice(sellPrice);
		salesOrderEntity.setTotal(total);
		salesOrderEntity.setUserId(userEntity.getId());
		salesOrderEntity.setPayType(payType);
		salesOrderEntity.setTagPrice(tagPrice);
		salesOrderEntity.setRemark(remark);

		// 新增出货记录
		result = this.insertSelective(salesOrderEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	@Transactional
	@Override
	public void updateSalesOrder(SalesOrderInfoModel order) {
		if (order == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}
		Long id = order.getId();
		String purchaseOrderName = order.getPurchaseOrderName();
		String goodsName = order.getGoodsName();
		Long colorId = order.getColorId();
		Long sizeId = order.getSizeId();
		BigDecimal sellPrice = order.getSellPrice();
		Long quantity = order.getQuantity();
		Integer payType = order.getPayType();
		BigDecimal tagPrice = order.getTagPrice();
		String remark = order.getRemark();

		if (id == null || StringUtils.isEmpty(purchaseOrderName) || StringUtils.isEmpty(goodsName) || colorId == null
				|| sizeId == null || sellPrice == null || quantity == null || payType == null || tagPrice == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		if (sellPrice.compareTo(BigDecimal.ZERO) <= 0 || quantity < 0) {
			throw new AppException(MessageInfoConstant.DATAS_INFO_IS_NOT_EXIST);
		}

		purchaseOrderName = purchaseOrderName.trim();
		goodsName = goodsName.trim();

		TbSalesOrderEntity salesOrderEntity = this.queryByPrimaryKey(id);
		if (salesOrderEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbStockEntity queryEntity = new TbStockEntity();
		queryEntity.setPurchaseOrderName(purchaseOrderName);
		queryEntity.setGoodsName(goodsName);
		queryEntity.setColorId(colorId);
		queryEntity.setSizeId(sizeId);
		TbStockEntity newStockEntity = tbStockMapper.queryInfoByEntity(queryEntity);
		if (newStockEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		int compareResult;
		long diff;
		int result;
		// 修改库存
		if (salesOrderEntity.getGoodsId().longValue() == newStockEntity.getId().longValue()) {
			// 同一件商品
			compareResult = salesOrderEntity.getQuantity().compareTo(quantity);
			if (compareResult > 0) {
				diff = salesOrderEntity.getQuantity().longValue() - quantity.longValue();
				result = tbStockMapper.addQty(newStockEntity.getId(), diff);
				if (result < 1) {
					throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
				}
			} else if (compareResult < 0) {
				diff = quantity.longValue() - salesOrderEntity.getQuantity().longValue();
				result = tbStockMapper.subtractQty(newStockEntity.getId(), diff);
				if (result < 1) {
					throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
				}
			}
		} else {
			// 不同商品
			// 原来商品新增库存
			result = tbStockMapper.addQty(salesOrderEntity.getGoodsId(), salesOrderEntity.getQuantity().longValue());
			if (result < 1) {
				throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
			}

			// 新商品扣减库存
			result = tbStockMapper.subtractQty(newStockEntity.getId(), quantity);
			if (result < 1) {
				throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
			}
		}

		BigDecimal quantity_bd = new BigDecimal(quantity);
		// 算出总价
		BigDecimal total = sellPrice.multiply(quantity_bd);

		TbUserEntity userEntity = (TbUserEntity) SecurityUtils.getSubject().getSession()
				.getAttribute(UserConstant.SESSION_LOGIN_USER_KEY);

		if (userEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbSalesOrderEntity insertOrderEntity = new TbSalesOrderEntity();
		insertOrderEntity.setId(id);
		insertOrderEntity.setGoodsId(newStockEntity.getId());
		insertOrderEntity.setColorId(colorId);
		insertOrderEntity.setSizeId(sizeId);
		insertOrderEntity.setQuantity(quantity);
		insertOrderEntity.setSellPrice(sellPrice);
		insertOrderEntity.setTotal(total);
		insertOrderEntity.setUserId(userEntity.getId());
		insertOrderEntity.setPayType(payType);
		insertOrderEntity.setTagPrice(tagPrice);
		insertOrderEntity.setRemark(remark);

		// 修改出货记录
		result = this.updateByPrimaryKeySelective(insertOrderEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

	}

	@Transactional
	@Override
	public void delSalesOrder(Long orderId) {
		if (orderId == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		TbSalesOrderEntity salesOrderEntity = this.queryByPrimaryKey(orderId);
		if (salesOrderEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbStockEntity stockEntity = tbStockMapper.queryByPrimaryKey(salesOrderEntity.getGoodsId());
		if (stockEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		long result = tbStockMapper.addQty(stockEntity.getId(), salesOrderEntity.getQuantity().longValue());
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		// 删除出货记录
		result = this.deleteByPrimaryKey(orderId);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

	}

	@Override
	public StatisticsInfoModel queryStatisticsInfo(int type, String date, Long userId) {
		// 销售总数
		StatisticsInfoModel statisticsInfo = tbSalesOrderMapper.queryStatisticsInfo(type, date, userId);
		// 支出总数
		BigDecimal consumeTotal = tbConsumeMapper.queryAmountCount(type, date);
		if (consumeTotal == null) {
			consumeTotal = BigDecimal.ZERO;
		}
		statisticsInfo.setConsumeTotal(consumeTotal);
		if (statisticsInfo.getSalesProfitTotal() == null) {
			statisticsInfo.setProfitTotal(consumeTotal.multiply(new BigDecimal("-1")).setScale(2, BigDecimal.ROUND_UP));
		} else {
			statisticsInfo.setProfitTotal(
					statisticsInfo.getSalesProfitTotal().subtract(consumeTotal).setScale(2, BigDecimal.ROUND_UP));
		}
		return statisticsInfo;
	}

}