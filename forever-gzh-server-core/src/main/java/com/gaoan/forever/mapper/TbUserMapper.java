package com.gaoan.forever.mapper;

import java.util.List;
import java.util.Map;

import com.gaoan.forever.base.BaseMapper;
import com.gaoan.forever.entity.TbUserEntity;

/**
 * 名称: TbUserMapper 描述: 用户Mapper接口 类型: JAVA
 */
public interface TbUserMapper extends BaseMapper<TbUserEntity> {

    public List<Map<String, Object>> queryUserResources(String userName);

    public List<Map<String, Object>> queryUserPage();
    
    public Map<String, Object> queryUserDetail(Long userId);

}