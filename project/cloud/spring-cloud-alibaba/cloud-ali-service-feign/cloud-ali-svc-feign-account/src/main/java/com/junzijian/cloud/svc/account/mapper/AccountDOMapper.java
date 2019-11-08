package com.junzijian.cloud.svc.account.mapper;

import com.junzijian.cloud.framework.model.account.entity.AccountDO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface AccountDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AccountDO record);

    int insertSelective(AccountDO record);

    AccountDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountDO record);

    int updateByPrimaryKey(AccountDO record);

    void decrAmount(@Param("userId") Long userId,
                    @Param("decrAmount") BigDecimal decrAmount);

    void incrAmount(@Param("userId") Long userId,
                    @Param("incrAmount") BigDecimal incrAmount);
}