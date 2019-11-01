package com.junzijian.cloud.svc.account.mapper;

import com.junzijian.cloud.framework.model.account.entity.AccountDO;

public interface AccountDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AccountDO record);

    int insertSelective(AccountDO record);

    AccountDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountDO record);

    int updateByPrimaryKey(AccountDO record);
}