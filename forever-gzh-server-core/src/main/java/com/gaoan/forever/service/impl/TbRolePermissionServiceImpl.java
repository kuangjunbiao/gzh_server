package com.gaoan.forever.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gaoan.forever.base.BaseService;
import com.gaoan.forever.entity.TbRolePermissionEntity;
import com.gaoan.forever.service.ITbRolePermissionService;

/**
 * 名称: TbRolePermissionServiceImpl 描述: 角色权限处理类 类型: JAVA
 * 
 */
@Service("TbRolePermissionServiceImpl")
public class TbRolePermissionServiceImpl extends BaseService<TbRolePermissionEntity>
	implements ITbRolePermissionService {

    private final Logger logger = LoggerFactory.getLogger(TbRolePermissionServiceImpl.class);

}