package com.bebopze.service.ucenter.mapper;

import com.bebopze.framework.model.ucenter.entity.ApiDO;

public interface ApiDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ApiDO record);

    int insertSelective(ApiDO record);

    ApiDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApiDO record);

    int updateByPrimaryKey(ApiDO record);
}