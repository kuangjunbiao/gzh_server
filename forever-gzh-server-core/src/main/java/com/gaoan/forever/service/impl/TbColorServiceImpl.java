package com.gaoan.forever.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbColorEntity;
import com.gaoan.forever.service.ITbColorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbColorServiceImpl 描述: 顏色处理类 类型: JAVA
 * 
 */
@Service("TbColorServiceImpl")
public class TbColorServiceImpl extends BaseService<TbColorEntity> implements ITbColorService {

	private final Logger logger = LoggerFactory.getLogger(TbColorServiceImpl.class);

	@Override
	public PageInfo<TbColorEntity> getColorPage(int pageNum, int pageSize) {
		logger.info("getColorPage");
		PageHelper.startPage(pageNum, pageSize);
		List<TbColorEntity> list = queryAll(null);
		return new PageInfo<>(list);
	}

	@Transactional
	@Override
	public void insertColorInfo(String colorName) {
		TbColorEntity colorEntity = new TbColorEntity();
		colorEntity.setColorName(colorName.trim());

		Long count = queryCount(colorEntity);
		if (count > 0) {
			throw new AppException(MessageInfoConstant.COLOR_IS_EXISTS);
		}

		int result = insertSelective(colorEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

	}

}