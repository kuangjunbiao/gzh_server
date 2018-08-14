package com.gaoan.forever.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaoan.forever.base.AppException;
import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.entity.TbResourcesEntity;
import com.gaoan.forever.mapper.TbRolePermissionMapper;
import com.gaoan.forever.service.ITbResourcesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 名称: TbResourcesServiceImpl 描述: 资源处理类 类型: JAVA
 * 
 */
@Service("TbResourcesServiceImpl")
public class TbResourcesServiceImpl extends BaseService<TbResourcesEntity> implements ITbResourcesService {

	private final Logger logger = LoggerFactory.getLogger(TbResourcesServiceImpl.class);

	@Autowired
	private TbRolePermissionMapper rolePermissionMapper;

	@Override
	public PageInfo<TbResourcesEntity> getResourcesPageInfo(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TbResourcesEntity> list = this.queryAll(null);
		return new PageInfo<TbResourcesEntity>(list);
	}

	@Override
	public TbResourcesEntity getResourcesDetail(Long resourcesId) {
		TbResourcesEntity resourcesEntity = this.queryByPrimaryKey(resourcesId);
		if (resourcesEntity == null) {
			throw new AppException(MessageInfoConstant.UPDATE_INFO_DONT_EXIST);
		}
		return resourcesEntity;
	}

	@Override
	public void insertResources(String resourcesName, Integer type) {
		if (StringUtils.isEmpty(resourcesName) || type == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		resourcesName = resourcesName.trim();

		TbResourcesEntity queryEntity = new TbResourcesEntity();
		queryEntity.setResourcesName(resourcesName);
		queryEntity.setType(type);
		TbResourcesEntity resourcesEntity = this.queryInfoByEntity(queryEntity);

		if (resourcesEntity != null) {
			throw new AppException(MessageInfoConstant.RESOURCES_NAME_IS_EXIST);
		}

		int result = this.insertSelective(queryEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	@Override
	public void updateResources(Long resourcesId, String resourcesName, Integer type) {
		if (resourcesId == null || StringUtils.isEmpty(resourcesName) || type == null) {
			throw new AppException(MessageInfoConstant.PARAM_CANT_BE_NULL);
		}

		resourcesName = resourcesName.trim();

		TbResourcesEntity queryEntity = new TbResourcesEntity();
		queryEntity.setResourcesName(resourcesName);
		queryEntity.setType(type);
		TbResourcesEntity resourcesEntity = this.queryInfoByEntity(queryEntity);

		if (resourcesEntity != null && resourcesEntity.getId().longValue() != resourcesId.longValue()) {
			throw new AppException(MessageInfoConstant.RESOURCES_NAME_IS_EXIST);
		}

		queryEntity.setId(resourcesId);
		int result = this.updateByPrimaryKeySelective(queryEntity);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}
	}

	/**
	 * 删除资源
	 */
	@Transactional
	@Override
	public void delResources(Long resourcesId) {
		// 删除资源
		int result = this.deleteByPrimaryKey(resourcesId);
		if (result < 1) {
			throw new AppException(MessageInfoConstant.COMMON_ERROR_CODE);
		}

		// 删除角色资源信息
		result = rolePermissionMapper.deleteByPermissionId(resourcesId);
		logger.info("删除资源, resourcesId = {}, 对应角色{}条", resourcesId, result);
	}

}