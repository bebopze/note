package com.junzijian.cloud.svc.storage.mapper;

import com.junzijian.cloud.framework.model.storage.entity.StorageDO;

public interface StorageDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StorageDO record);

    int insertSelective(StorageDO record);

    StorageDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StorageDO record);

    int updateByPrimaryKey(StorageDO record);
}