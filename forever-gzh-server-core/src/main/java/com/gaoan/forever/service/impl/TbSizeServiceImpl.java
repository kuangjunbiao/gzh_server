package com.gaoan.forever.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbSizeEntity;
import com.gaoan.forever.service.ITbSizeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbSizeServiceImpl 描述: 尺寸处理类 类型: JAVA
 * 
 */
@Service("TbSizeServiceImpl")
public class TbSizeServiceImpl extends BaseService<TbSizeEntity> implements ITbSizeService {

	private final Logger logger = LoggerFactory.getLogger(TbSizeServiceImpl.class);

	@Override
	public PageInfo<TbSizeEntity> getPageInfo(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbSizeEntity> list = queryAll(null);
		return new PageInfo<>(list);
	}

	@Override
	public void insertSizeInfo(String sizeName, String sizeCode) {
		TbSizeEntity sizeEntity = new TbSizeEntity();
		sizeEntity.setSizeName(sizeName.trim());
		sizeEntity.setSizeCode(sizeCode.trim());
		
		Long count = queryCount(sizeEntity);
		if(count > 0){
			throw new AppException(MessageInfoConstant.SIZE_IS_EXISTS);
		}
		
		int result = insertSelective(sizeEntity);
		if(result < 1){
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
			
		}
	}

}