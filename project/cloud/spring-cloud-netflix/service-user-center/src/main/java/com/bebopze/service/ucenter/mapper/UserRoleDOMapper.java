package com.bebopze.service.ucenter.mapper;

import com.bebopze.framework.model.ucenter.entity.UserRoleDO;
import com.bebopze.framework.model.ucenter.entity.UserRoleDOKey;

public interface UserRoleDOMapper {
    int deleteByPrimaryKey(UserRoleDOKey key);

    int insert(UserRoleDO record);

    int insertSelective(UserRoleDO record);

    UserRoleDO selectByPrimaryKey(UserRoleDOKey key);

    int updateByPrimaryKeySelective(UserRoleDO record);

    int updateByPrimaryKey(UserRoleDO record);
}