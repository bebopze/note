package com.junzijian.service.ucenter.mapper;

import com.junzijian.framework.model.ucenter.entity.PermissionApiDO;
import com.junzijian.framework.model.ucenter.entity.PermissionApiDOKey;

public interface PermissionApiDOMapper {
    int deleteByPrimaryKey(PermissionApiDOKey key);

    int insert(PermissionApiDO record);

    int insertSelective(PermissionApiDO record);

    PermissionApiDO selectByPrimaryKey(PermissionApiDOKey key);

    int updateByPrimaryKeySelective(PermissionApiDO record);

    int updateByPrimaryKey(PermissionApiDO record);
}