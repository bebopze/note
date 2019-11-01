package com.junzijian.cloud.service.ucenter.mapper;

import com.junzijian.framework.model.ucenter.entity.UserRoleDO;
import com.junzijian.framework.model.ucenter.entity.UserRoleDOKey;

public interface UserRoleDOMapper {
    int deleteByPrimaryKey(UserRoleDOKey key);

    int insert(UserRoleDO record);

    int insertSelective(UserRoleDO record);

    UserRoleDO selectByPrimaryKey(UserRoleDOKey key);

    int updateByPrimaryKeySelective(UserRoleDO record);

    int updateByPrimaryKey(UserRoleDO record);
}