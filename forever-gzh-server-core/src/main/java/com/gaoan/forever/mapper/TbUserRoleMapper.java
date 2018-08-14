package com.gaoan.forever.mapper;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbUserRoleEntity;

/**
 * 名称: TbUserRoleMapper 描述: 用户角色Mapper接口 类型: JAVA
 */
public interface TbUserRoleMapper extends BaseMapper<TbUserRoleEntity> {

    int updateRoleByUserId(TbUserRoleEntity userRoleEntity);

    int deleteByUserId(Long userId);

    int deleteByRoleId(Long roleId);
}