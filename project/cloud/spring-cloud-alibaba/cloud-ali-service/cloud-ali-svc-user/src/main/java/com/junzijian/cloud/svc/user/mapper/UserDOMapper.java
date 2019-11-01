package com.junzijian.cloud.svc.user.mapper;

import com.junzijian.cloud.framework.model.user.entity.UserDO;

public interface UserDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}