package com.gaoan.forever.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.entity.TbUserRoleEntity;
import com.gaoan.forever.mapper.TbUserRoleMapper;
import com.gaoan.forever.service.ITbUserRoleService;

/**
 * 名称: TbUserRoleServiceImpl 描述: 用户处理类 类型: JAVA
 * 
 */
@Service("TbUserRoleServiceImpl")
public class TbUserRoleServiceImpl extends BaseService<TbUserRoleEntity> implements ITbUserRoleService {

    private final Logger logger = LoggerFactory.getLogger(TbUserRoleServiceImpl.class);

    @Autowired
    private TbUserRoleMapper tbUserRoleMapper;

    @Override
    public int deleteByUserId(Long userId) {
	int result = tbUserRoleMapper.deleteByUserId(userId);
	return result;
    }

}