package com.gaoan.forever.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbConsumeEntity;
import com.gaoan.forever.service.ITbConsumeService;
import com.gaoan.forever.utils.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbConsumeServiceImpl 描述: 消费类 类型: JAVA
 * 
 */
@Service("TbConsumeServiceImpl")
public class TbConsumeServiceImpl extends BaseService<TbConsumeEntity> implements ITbConsumeService {

	private final Logger logger = LoggerFactory.getLogger(TbConsumeServiceImpl.class);

	@Override
	public PageInfo<TbConsumeEntity> getConsumePageInfo(Map<String, String> param, int pageNum, int pageSize) {
		logger.info("start getConsumePageInfo.");

		PageHelper.startPage(pageNum, pageSize);

		TbConsumeEntity queryEntity = new TbConsumeEntity();
		if (param != null && !param.isEmpty()) {
			queryEntity.setDate(DateUtil.parseDateY1(param.get("date")));
		}
		List<TbConsumeEntity> list = this.queryAll(queryEntity);

		return new PageInfo<>(list);
	}

	@Override
	public TbConsumeEntity getConsumeDetail(Long id) {
		if (id == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		TbConsumeEntity entity = this.queryByPrimaryKey(id);
		if (entity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}
		return entity;
	}

	@Override
	public void insertConsumeInfo(TbConsumeEntity entity) {
		if (entity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		Date date = entity.getDate();
		BigDecimal amount = entity.getAmount();

		if (date == null || amount == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new AppException(MessageInfoConstant.DATAS_INFO_IS_NOT_EXIST);
		}

		int result = this.insertSelective(entity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	@Override
	public void updateConsumeInfo(TbConsumeEntity entity) {
		if (entity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}

		Long id = entity.getId();
		Date date = entity.getDate();
		BigDecimal amount = entity.getAmount();

		if (id == null || date == null || amount == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new AppException(MessageInfoConstant.DATAS_INFO_IS_NOT_EXIST);
		}

		int result = this.updateByPrimaryKeySelective(entity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	@Override
	public void deleteConsumeInfo(Long id) {
		if (id == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		int result = this.deleteByPrimaryKey(id);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

}