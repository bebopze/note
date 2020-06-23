package com.bebopze.cloud.svc.storage.mapper;

import com.bebopze.cloud.framework.model.storage.entity.StorageDO;
import org.apache.ibatis.annotations.Param;

public interface StorageDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StorageDO record);

    int insertSelective(StorageDO record);

    StorageDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StorageDO record);

    int updateByPrimaryKey(StorageDO record);

    int decrInventory(@Param("productId") Long productId,
                      @Param("decrNum") int decrNum);

    int incrInventory(@Param("productId") Long productId,
                      @Param("incrNum") int incrNum);
}