package com.gaoan.forever.service;

import com.gaoan.forever.base.IBaseService;
import com.gaoan.forever.entity.TbResourcesEntity;
import com.github.pagehelper.PageInfo;

/**
 * 名称: ITbResourcesService 描述: 资源信息Service接口 类型: JAVA
 * 
 */
public interface ITbResourcesService extends IBaseService<TbResourcesEntity> {

    public TbResourcesEntity getResourcesDetail(Long resourcesId);

    public PageInfo<TbResourcesEntity> getResourcesPageInfo(int pageNum, int pageSize);

    public void insertResources(String resourcesName, Integer type);

    public void updateResources(Long resourcesId, String resourcesName, Integer type);

    public void delResources(Long resourcesId);

}