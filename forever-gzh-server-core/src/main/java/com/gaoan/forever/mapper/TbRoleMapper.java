package com.gaoan.forever.mapper;

import java.util.List;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbRoleEntity;

/**
 * 名称: TbRoleMapper 描述: 角色Mapper接口 类型: JAVA
 */
public interface TbRoleMapper extends BaseMapper<TbRoleEntity> {

    public List<Long> queryRolePermission(Long roleId);
}