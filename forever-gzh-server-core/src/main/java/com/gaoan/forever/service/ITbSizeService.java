package com.gaoan.forever.service;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbSizeEntity;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbSizeService 描述: 尺寸Service接口 类型: JAVA
 * 
 */
public interface ITbSizeService extends IBaseService<TbSizeEntity> {

	PageInfo<TbSizeEntity> getPageInfo(int pageNum, int pageSize);

	void insertSizeInfo(String sizeName, String sizeCode);
}