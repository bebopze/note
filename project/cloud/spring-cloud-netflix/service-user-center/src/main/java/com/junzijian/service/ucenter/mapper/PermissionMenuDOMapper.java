package com.junzijian.service.ucenter.mapper;

import com.junzijian.framework.model.ucenter.entity.PermissionMenuDO;
import com.junzijian.framework.model.ucenter.entity.PermissionMenuDOKey;

public interface PermissionMenuDOMapper {
    int deleteByPrimaryKey(PermissionMenuDOKey key);

    int insert(PermissionMenuDO record);

    int insertSelective(PermissionMenuDO record);

    PermissionMenuDO selectByPrimaryKey(PermissionMenuDOKey key);

    int updateByPrimaryKeySelective(PermissionMenuDO record);

    int updateByPrimaryKey(PermissionMenuDO record);
}