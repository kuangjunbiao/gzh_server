package com.gaoan.forever.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.constant.UserConstant;
import com.gaoan.forever.entity.TbPurchaseOrderEntity;
import com.gaoan.forever.entity.TbStockEntity;
import com.gaoan.forever.entity.TbUserEntity;
import com.gaoan.forever.mapper.TbPurchaseOrderMapper;
import com.gaoan.forever.mapper.TbStockMapper;
import com.gaoan.forever.model.query.OrderQueryConditionModel;
import com.gaoan.forever.model.result.PurchaseOrderInfoModel;
import com.gaoan.forever.service.ITbPurchaseOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbPurchaseOrderServiceImpl 描述: 角色处理类 类型: JAVA
 * 
 */
@Service("TbPurchaseOrderServiceImpl")
public class TbPurchaseOrderServiceImpl extends BaseService<TbPurchaseOrderEntity> implements ITbPurchaseOrderService {

	private final Logger logger = LoggerFactory.getLogger(TbPurchaseOrderServiceImpl.class);

	@Autowired
	private TbPurchaseOrderMapper tbPurchaseOrderMapper;

	@Autowired
	private TbStockMapper tbStockMapper;

	@Override
	public PageInfo<PurchaseOrderInfoModel> queryPurchaseOrder(OrderQueryConditionModel condition, int page,
			int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<PurchaseOrderInfoModel> list = tbPurchaseOrderMapper.queryPurchaseOrder(condition);
		return new PageInfo<>(list);
	}

	@Override
	public PurchaseOrderInfoModel queryPurchaseDetail(Long id) {
		PurchaseOrderInfoModel model = tbPurchaseOrderMapper.queryPurchaseDetail(id);
		return model;
	}

	@Transactional
	@Override
	public void insertPurchaseOrderByHave(PurchaseOrderInfoModel model) {
		if (model == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		String purchaseOrderName = model.getPurchaseOrderName();
		String goodsName = model.getGoodsName();
		Long colorId = model.getColorId();
		Long sizeId = model.getSizeId();
		BigDecimal costPrice = model.getCostPrice();
		Long quantity = model.getQuantity();

		if (StringUtils.isEmpty(purchaseOrderName) || StringUtils.isEmpty(goodsName) || colorId == null
				|| sizeId == null || costPrice == null || quantity == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}
		purchaseOrderName = purchaseOrderName.trim();
		goodsName = goodsName.trim();

		if (costPrice.compareTo(BigDecimal.ZERO) <= 0 || quantity < 0) {
			throw new AppException(MessageInfoConstant.DATAS_INFO_IS_NOT_EXIST);
		}

		BigDecimal currQty = new BigDecimal(quantity);
		// 算出总价
		BigDecimal currTotal = costPrice.multiply(currQty);

		TbStockEntity stockEntity = initStock(purchaseOrderName, goodsName, colorId, sizeId, costPrice, quantity);

		TbUserEntity userEntity = (TbUserEntity) SecurityUtils.getSubject().getSession()
				.getAttribute(UserConstant.SESSION_LOGIN_USER_KEY);

		if (userEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbPurchaseOrderEntity purchaseOrderEntity = new TbPurchaseOrderEntity();
		purchaseOrderEntity.setGoodsId(stockEntity.getId());
		purchaseOrderEntity.setColorId(colorId);
		purchaseOrderEntity.setSizeId(sizeId);
		purchaseOrderEntity.setQuantity(quantity);
		purchaseOrderEntity.setCostPrice(costPrice);
		purchaseOrderEntity.setTotal(currTotal);
		purchaseOrderEntity.setUserId(userEntity.getId());

		// 新增进货记录
		int result = this.insertSelective(purchaseOrderEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	/**
	 * 新增已有后,修改库存信息
	 * 
	 * @param purchaseOrderName
	 * @param goodsName
	 * @param colorId
	 * @param sizeId
	 * @param costPrice
	 * @param quantity
	 * @return
	 */
	private TbStockEntity initStock(String purchaseOrderName, String goodsName, Long colorId, Long sizeId,
			BigDecimal costPrice, Long quantity) {
		// 修改库存
		TbStockEntity queryEntity = new TbStockEntity();
		queryEntity.setPurchaseOrderName(purchaseOrderName);
		queryEntity.setGoodsName(goodsName);
		queryEntity.setColorId(colorId);
		queryEntity.setSizeId(sizeId);
		TbStockEntity stockEntity = tbStockMapper.queryInfoByEntity(queryEntity);
		int result;
		if (stockEntity == null) {
			stockEntity = new TbStockEntity();
			stockEntity.setPurchaseOrderName(purchaseOrderName);
			stockEntity.setGoodsName(goodsName);
			stockEntity.setColorId(colorId);
			stockEntity.setSizeId(sizeId);
			stockEntity.setQuantity(quantity);
			stockEntity.setAvgPrice(costPrice);

			result = tbStockMapper.insertSelective(stockEntity);
			if (result < 1) {
				throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
			}
		} else {
			result = tbStockMapper.addQty(stockEntity.getId(), quantity);
			if (result < 1) {
				throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
			}

			calcAvgPrice(stockEntity.getId(), costPrice, quantity, 1, false, null);
		}
		return stockEntity;
	}

	/**
	 * 重新计算商品平均价
	 * 
	 * @param stockId
	 * @param costPrice
	 * @param diff
	 * @param flag
	 *            1:数量增加 2:数量减少
	 * @param isSameGood
	 *            true: 修改同一件商品的进货单, false:其他情况
	 * @param orderId
	 *            isSameGood=true才需要
	 */
	private void calcAvgPrice(Long stockId, BigDecimal costPrice, Long diff, int flag, boolean isSameGood,
			Long orderId) {
		TbStockEntity stockEntity = tbStockMapper.queryByPrimaryKey(stockId);
		if (stockEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}
		Map<String, Object> map = null;
		// 计算目前总数及总价
		if (isSameGood) {
			map = tbPurchaseOrderMapper.quertTotalExcludeCurrOrder(stockId, orderId);
		} else {
			map = tbPurchaseOrderMapper.quertCurrTotal(stockId);
		}

		if (map == null) {
			logger.error("stockId = {}, 库存找不到.", stockId);
			map = new HashMap<String, Object>();
			map.put("qty", BigDecimal.ZERO);
			map.put("total", BigDecimal.ZERO);
		} else if (map.isEmpty()) {
			map.put("qty", BigDecimal.ZERO);
			map.put("total", BigDecimal.ZERO);
		}

		MathContext mc = new MathContext(ForeverConstant.VIRTUAL_CURRENCY_DECIMALCOUNT, RoundingMode.UP);
		BigDecimal avgPrice = null;
		if (flag == 1) {
			String qtyStr = ((BigDecimal) map.get("qty")).toPlainString();
			String totalStr = ((BigDecimal) map.get("total")).toPlainString();
			if (StringUtils.isEmpty(qtyStr) || StringUtils.isEmpty(totalStr)) {
				throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
			}
			BigDecimal qty = new BigDecimal(qtyStr);
			BigDecimal total = new BigDecimal(totalStr);
			BigDecimal diffQty = new BigDecimal(diff);

			avgPrice = (total.add(diffQty.multiply(costPrice))).divide(qty.add(diffQty), mc);

		} else if (flag == 2) {
			String qtyStr = ((BigDecimal) map.get("qty")).toPlainString();
			String totalStr = ((BigDecimal) map.get("total")).toPlainString();
			if (StringUtils.isEmpty(qtyStr) || StringUtils.isEmpty(totalStr)) {
				throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
			}
			BigDecimal qty = new BigDecimal(qtyStr);
			BigDecimal total = new BigDecimal(totalStr);
			BigDecimal diffQty = new BigDecimal(diff);

			if (qty.subtract(diffQty).compareTo(BigDecimal.ZERO) == 0) {
				avgPrice = BigDecimal.ZERO;
			} else {
				avgPrice = total.subtract(diffQty.multiply(costPrice)).divide(qty.subtract(diffQty), mc);
			}
		}

		stockEntity.setAvgPrice(avgPrice);
		int result = tbStockMapper.updateByPrimaryKeySelective(stockEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

	}

	@Transactional
	@Override
	public void insertPurchaseOrderByUnHave(PurchaseOrderInfoModel model) {
		if (model == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		String purchaseOrderName = model.getPurchaseOrderName();
		String goodsName = model.getGoodsName();
		Long colorId = model.getColorId();
		Long sizeId = model.getSizeId();
		BigDecimal costPrice = model.getCostPrice();
		Long quantity = model.getQuantity();

		if (StringUtils.isEmpty(purchaseOrderName) || StringUtils.isEmpty(goodsName) || colorId == null
				|| sizeId == null || costPrice == null || quantity == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		if (costPrice.compareTo(BigDecimal.ZERO) <= 0 || quantity < 0) {
			throw new AppException(MessageInfoConstant.DATAS_INFO_IS_NOT_EXIST);
		}

		purchaseOrderName = purchaseOrderName.trim();
		goodsName = goodsName.trim();

		// 查询库存
		TbStockEntity queryEntity = new TbStockEntity();
		queryEntity.setPurchaseOrderName(purchaseOrderName);
		queryEntity.setGoodsName(goodsName);
		queryEntity.setColorId(colorId);
		queryEntity.setSizeId(sizeId);
		TbStockEntity stockEntity = tbStockMapper.queryInfoByEntity(queryEntity);
		if (stockEntity != null) {
			throw new AppException(MessageInfoConstant.PRODUCT_ALREADY_EXISTS);
		}

		queryEntity.setQuantity(quantity);
		queryEntity.setAvgPrice(costPrice);
		int result = tbStockMapper.insertSelective(queryEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		BigDecimal currQty = new BigDecimal(quantity);
		// 算出总价
		BigDecimal currTotal = costPrice.multiply(currQty);

		TbUserEntity userEntity = (TbUserEntity) SecurityUtils.getSubject().getSession()
				.getAttribute(UserConstant.SESSION_LOGIN_USER_KEY);

		if (userEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbPurchaseOrderEntity purchaseOrderEntity = new TbPurchaseOrderEntity();
		purchaseOrderEntity.setGoodsId(queryEntity.getId());
		purchaseOrderEntity.setColorId(colorId);
		purchaseOrderEntity.setSizeId(sizeId);
		purchaseOrderEntity.setQuantity(quantity);
		purchaseOrderEntity.setCostPrice(costPrice);
		purchaseOrderEntity.setTotal(currTotal);
		purchaseOrderEntity.setUserId(userEntity.getId());

		// 新增进货记录
		result = this.insertSelective(purchaseOrderEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	@Transactional
	@Override
	public void updatePurchaseOrder(PurchaseOrderInfoModel order) {
		if (order == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}
		Long id = order.getId();
		String purchaseOrderName = order.getPurchaseOrderName();
		String goodsName = order.getGoodsName();
		Long colorId = order.getColorId();
		Long sizeId = order.getSizeId();
		BigDecimal costPrice = order.getCostPrice();
		Long quantity = order.getQuantity();

		if (id == null || StringUtils.isEmpty(purchaseOrderName) || StringUtils.isEmpty(goodsName) || colorId == null
				|| sizeId == null || costPrice == null || quantity == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		if (costPrice.compareTo(BigDecimal.ZERO) <= 0 || quantity < 0) {
			throw new AppException(MessageInfoConstant.DATAS_INFO_IS_NOT_EXIST);
		}

		purchaseOrderName = purchaseOrderName.trim();
		goodsName = goodsName.trim();

		TbPurchaseOrderEntity purchaseOrderEntity = this.queryByPrimaryKey(id);
		if (purchaseOrderEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		int result;
		TbStockEntity queryEntity = new TbStockEntity();
		queryEntity.setPurchaseOrderName(purchaseOrderName);
		queryEntity.setGoodsName(goodsName);
		queryEntity.setColorId(colorId);
		queryEntity.setSizeId(sizeId);
		TbStockEntity newStockEntity = tbStockMapper.queryInfoByEntity(queryEntity);
		if (newStockEntity == null) {
			// 原进货订单,减少库存
			result = tbStockMapper.subtractQty(purchaseOrderEntity.getGoodsId(),
					purchaseOrderEntity.getQuantity().longValue());
			if (result < 1) {
				throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
			}
			calcAvgPrice(purchaseOrderEntity.getGoodsId(), purchaseOrderEntity.getCostPrice(),
					purchaseOrderEntity.getQuantity(), 2, false, null);

			// 新进货订单,新增库存
			newStockEntity = new TbStockEntity();
			newStockEntity.setPurchaseOrderName(purchaseOrderName);
			newStockEntity.setGoodsName(goodsName);
			newStockEntity.setColorId(colorId);
			newStockEntity.setSizeId(sizeId);
			newStockEntity.setQuantity(quantity);
			newStockEntity.setAvgPrice(costPrice);

			result = tbStockMapper.insertSelective(newStockEntity);
			if (result < 1) {
				throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
			}
		} else {
			int compareResult;
			long diff;
			// 修改库存
			if (purchaseOrderEntity.getGoodsId().longValue() == newStockEntity.getId().longValue()) {
				// 同一件商品
				compareResult = purchaseOrderEntity.getQuantity().compareTo(quantity);
				if (compareResult > 0) {
					diff = purchaseOrderEntity.getQuantity().longValue() - quantity.longValue();
					result = tbStockMapper.subtractQty(newStockEntity.getId(), diff);
					if (result < 1) {
						throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
					}
				} else if (compareResult < 0) {
					diff = quantity.longValue() - purchaseOrderEntity.getQuantity().longValue();
					result = tbStockMapper.addQty(newStockEntity.getId(), diff);
					if (result < 1) {
						throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
					}
				}
				calcAvgPrice(purchaseOrderEntity.getGoodsId(), costPrice, quantity, 1, true,
						purchaseOrderEntity.getId());
			} else {
				// 不同商品
				// 原来商品减少库存
				result = tbStockMapper.subtractQty(purchaseOrderEntity.getGoodsId(),
						purchaseOrderEntity.getQuantity().longValue());
				if (result < 1) {
					throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
				}
				calcAvgPrice(purchaseOrderEntity.getGoodsId(), purchaseOrderEntity.getCostPrice(),
						purchaseOrderEntity.getQuantity(), 2, false, null);

				// 新商品新增库存
				result = tbStockMapper.addQty(newStockEntity.getId(), quantity);
				if (result < 1) {
					throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
				}
				calcAvgPrice(newStockEntity.getId(), costPrice, quantity, 1, false, null);
			}
		}

		BigDecimal currQty = new BigDecimal(quantity);
		// 算出总价
		BigDecimal currTotal = costPrice.multiply(currQty);

		TbUserEntity userEntity = (TbUserEntity) SecurityUtils.getSubject().getSession()
				.getAttribute(UserConstant.SESSION_LOGIN_USER_KEY);

		if (userEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbPurchaseOrderEntity updateOrderEntity = new TbPurchaseOrderEntity();
		updateOrderEntity.setId(id);
		updateOrderEntity.setGoodsId(newStockEntity.getId());
		updateOrderEntity.setColorId(colorId);
		updateOrderEntity.setSizeId(sizeId);
		updateOrderEntity.setQuantity(quantity);
		updateOrderEntity.setCostPrice(costPrice);
		updateOrderEntity.setTotal(currTotal);
		updateOrderEntity.setUserId(userEntity.getId());

		// 修改进货记录
		result = this.updateByPrimaryKeySelective(updateOrderEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

	}

	@Transactional
	@Override
	public void delPurchaseOrder(Long orderId) {
		if (orderId == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		TbPurchaseOrderEntity purchaseOrderEntity = this.queryByPrimaryKey(orderId);
		if (purchaseOrderEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		TbStockEntity stockEntity = tbStockMapper.queryByPrimaryKey(purchaseOrderEntity.getGoodsId());
		if (stockEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		long result = tbStockMapper.subtractQty(stockEntity.getId(), purchaseOrderEntity.getQuantity().longValue());
		if (result < 1) {
			throw new AppException(MessageInfoConstant.NOT_ENOUGH_INVENTORY);
		}

		// 重算平均价
		calcAvgPrice(stockEntity.getId(), purchaseOrderEntity.getCostPrice(), purchaseOrderEntity.getQuantity(), 2,
				false, null);

		// 删除进货记录
		result = this.deleteByPrimaryKey(orderId);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
		// 如果只存在当前进货数据,那么删除库存记录
		stockEntity = tbStockMapper.queryByPrimaryKey(purchaseOrderEntity.getGoodsId());
		if (stockEntity.getQuantity().longValue() == 0) {
			TbPurchaseOrderEntity queryEntity = new TbPurchaseOrderEntity();
			queryEntity.setGoodsId(purchaseOrderEntity.getGoodsId());
			List<TbPurchaseOrderEntity> list = this.queryAll(queryEntity);

			if (CollectionUtils.isEmpty(list)) {
				tbStockMapper.deleteByPrimaryKey(stockEntity.getId());
				if (result < 1) {
					throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
				}
			}
		}

	}
}