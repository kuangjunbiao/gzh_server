package com.gaoan.forever.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbConsumeEntity;

/**
 * 名称: TbConsumeMapper 描述: 消费Mapper接口 类型: JAVA
 */
public interface TbConsumeMapper extends BaseMapper<TbConsumeEntity> {

	BigDecimal queryAmountCount(@Param("type") int type, @Param("date") String date);

}