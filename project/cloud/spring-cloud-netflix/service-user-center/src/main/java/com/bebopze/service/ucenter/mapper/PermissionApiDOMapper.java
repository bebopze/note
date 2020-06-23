package com.bebopze.service.ucenter.mapper;

import com.bebopze.framework.model.ucenter.entity.PermissionApiDO;
import com.bebopze.framework.model.ucenter.entity.PermissionApiDOKey;

public interface PermissionApiDOMapper {
    int deleteByPrimaryKey(PermissionApiDOKey key);

    int insert(PermissionApiDO record);

    int insertSelective(PermissionApiDO record);

    PermissionApiDO selectByPrimaryKey(PermissionApiDOKey key);

    int updateByPrimaryKeySelective(PermissionApiDO record);

    int updateByPrimaryKey(PermissionApiDO record);
}