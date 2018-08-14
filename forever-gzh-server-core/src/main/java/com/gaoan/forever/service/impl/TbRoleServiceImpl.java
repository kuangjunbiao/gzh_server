package com.gaoan.forever.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbRoleEntity;
import com.gaoan.forever.entity.TbRolePermissionEntity;
import com.gaoan.forever.entity.TbUserRoleEntity;
import com.gaoan.forever.mapper.TbRoleMapper;
import com.gaoan.forever.mapper.TbRolePermissionMapper;
import com.gaoan.forever.mapper.TbUserRoleMapper;
import com.gaoan.forever.service.ITbRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbRoleServiceImpl 描述: 角色处理类 类型: JAVA
 * 
 */
@Service("TbRoleServiceImpl")
public class TbRoleServiceImpl extends BaseService<TbRoleEntity> implements ITbRoleService {

	private final Logger logger = LoggerFactory.getLogger(TbRoleServiceImpl.class);

	@Autowired
	private TbRoleMapper tbRoleMapper;

	@Autowired
	private TbUserRoleMapper tbUserRoleMapper;

	@Autowired
	private TbRolePermissionMapper tbRolePermissionMapper;

	@Override
	public List<Long> queryRolePermission(Long roleId) {
		List<Long> list = tbRoleMapper.queryRolePermission(roleId);
		return list;
	}

	@Override
	public PageInfo<TbRoleEntity> getRolePageInfo(int page, int pageSize) {
		PageHelper.startPage(page, pageSize);
		List<TbRoleEntity> list = this.queryAll(null);
		return new PageInfo<TbRoleEntity>(list);
	}

	@Override
	public void insertRole(String roleName, List<Long> resourcesIdList) {
		if (StringUtils.isEmpty(roleName)) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		roleName = roleName.trim();

		TbRoleEntity queryEntity = new TbRoleEntity();
		queryEntity.setRoleName(roleName);
		TbRoleEntity roleEntity = tbRoleMapper.queryInfoByEntity(queryEntity);

		if (roleEntity != null) {
			throw new AppException(MessageInfoConstant.ROLE_NAME_IS_EXIST);
		}

		int result = tbRoleMapper.insertSelective(queryEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		if (CollectionUtils.isEmpty(resourcesIdList)) {
			return;
		}

		// 新增角色对应权限
		List<TbRolePermissionEntity> list = new ArrayList<TbRolePermissionEntity>();
		TbRolePermissionEntity rolePermissionEntity;
		for (Long resourcesId : resourcesIdList) {
			rolePermissionEntity = new TbRolePermissionEntity();
			rolePermissionEntity.setRoleId(queryEntity.getId());
			rolePermissionEntity.setResourcesId(resourcesId);

			list.add(rolePermissionEntity);
		}

		result = tbRolePermissionMapper.batchInsert(list);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	@Transactional
	@Override
	public void updateRole(Long roleId, String roleName, List<Long> resourcesIdList) {
		if (roleId == null || StringUtils.isEmpty(roleName)) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		roleName = roleName.trim();

		// 判断名字是否重复
		TbRoleEntity queryEntity = new TbRoleEntity();
		queryEntity.setRoleName(roleName);
		TbRoleEntity roleEntity = tbRoleMapper.queryInfoByEntity(queryEntity);

		if (roleEntity != null && roleEntity.getId().longValue() != roleId.longValue()) {
			throw new AppException(MessageInfoConstant.ROLE_NAME_IS_EXIST);
		}

		// 修改角色信息
		queryEntity.setId(roleId);
		int result = tbRoleMapper.updateByPrimaryKeySelective(queryEntity);
		if (result < 1) {
			logger.error("roleId = {}, 修改失败.", roleId);
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		// 删除角色对应权限
		result = tbRolePermissionMapper.deleteByRoleId(roleId);
		logger.error("删除roleId = {}, 对应权限{}条.", roleId, result);

		if (CollectionUtils.isEmpty(resourcesIdList)) {
			return;
		}

		// 新增角色对应权限
		List<TbRolePermissionEntity> list = new ArrayList<TbRolePermissionEntity>();
		TbRolePermissionEntity rolePermissionEntity;
		for (Long resourcesId : resourcesIdList) {
			rolePermissionEntity = new TbRolePermissionEntity();
			rolePermissionEntity.setRoleId(roleId);
			rolePermissionEntity.setResourcesId(resourcesId);

			list.add(rolePermissionEntity);
		}

		result = tbRolePermissionMapper.batchInsert(list);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	@Transactional
	@Override
	public void delRole(Long roleId) {
		if (roleId == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		// 查看角色是否已被使用
		TbUserRoleEntity queryUserRoleEntity = new TbUserRoleEntity();
		queryUserRoleEntity.setRoleId(roleId);
		Long count = tbUserRoleMapper.queryCount(queryUserRoleEntity);
		if (count > 0) {
			throw new AppException(MessageInfoConstant.ROLE_INFO_IS_USE);
		}

		// 删除角色权限信息
		int result = tbRolePermissionMapper.deleteByRoleId(roleId);
		logger.info("删除角色编号:{}, 对应{}条权限信息.", roleId, result);

		// 删除角色
		result = tbRoleMapper.deleteByPrimaryKey(roleId);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		// 删除用户角色信息
		// result = tbUserRoleMapper.deleteByRoleId(roleId);
		// logger.info("删除角色编号:{}, 对应{}条用户信息.", roleId, result);
	}

}