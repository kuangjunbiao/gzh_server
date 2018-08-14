package com.gaoan.forever.service;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbUserRoleEntity;

/**
 * 名称: ITbUserRoleService 描述: 用户角色Service接口 类型: JAVA
 * 
 */
public interface ITbUserRoleService extends IBaseService<TbUserRoleEntity> {

    public int deleteByUserId(Long userId);
}