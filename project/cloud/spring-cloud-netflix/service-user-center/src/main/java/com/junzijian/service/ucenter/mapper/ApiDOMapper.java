package com.junzijian.service.ucenter.mapper;

import com.junzijian.framework.model.ucenter.entity.ApiDO;

public interface ApiDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ApiDO record);

    int insertSelective(ApiDO record);

    ApiDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApiDO record);

    int updateByPrimaryKey(ApiDO record);
}