package com.gaoan.forever.service;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbColorEntity;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbColorService 描述: 颜色Service接口 类型: JAVA
 * 
 */
public interface ITbColorService extends IBaseService<TbColorEntity> {

	PageInfo<TbColorEntity> getColorPage(int pageNum, int pageSize);

	void insertColorInfo(String colorName);

}