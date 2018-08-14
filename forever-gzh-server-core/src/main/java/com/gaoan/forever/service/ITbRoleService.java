package com.gaoan.forever.service;

import java.util.List;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbRoleEntity;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbRoleService 描述: 角色信息Service接口 类型: JAVA
 * 
 */
public interface ITbRoleService extends IBaseService<TbRoleEntity> {

	public PageInfo<TbRoleEntity> getRolePageInfo(int page, int pageSize);

	public List<Long> queryRolePermission(Long roleId);

	public void insertRole(String roleName, List<Long> resourcesIdList);

	public void updateRole(Long roleId, String roleName, List<Long> resourcesIdList);

	public void delRole(Long roleId);
}