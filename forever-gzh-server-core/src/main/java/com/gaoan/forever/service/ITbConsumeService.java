package com.gaoan.forever.service;

import java.util.Map;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbConsumeEntity;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbConsumeService 描述: 消费Service接口 类型: JAVA
 * 
 */
public interface ITbConsumeService extends IBaseService<TbConsumeEntity> {

	PageInfo<TbConsumeEntity> getConsumePageInfo(Map<String, String> param, int pageNum, int pageSize);

	TbConsumeEntity getConsumeDetail(Long id);

	void insertConsumeInfo(TbConsumeEntity entity);

	void updateConsumeInfo(TbConsumeEntity entity);

	void deleteConsumeInfo(Long id);
}