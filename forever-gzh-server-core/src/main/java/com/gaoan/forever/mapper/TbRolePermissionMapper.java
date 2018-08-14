package com.gaoan.forever.mapper;

import java.util.List;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbRolePermissionEntity;

/**
 * 名称: TbRolePermissionMapper 描述: 角色资源Mapper接口 类型: JAVA
 */
public interface TbRolePermissionMapper extends BaseMapper<TbRolePermissionEntity> {

    public int deleteByRoleId(Long roleId);

    public int deleteByPermissionId(Long resourcesId);

    public int batchInsert(List<TbRolePermissionEntity> list);

}