package com.bebopze.cloud.svc.product.mapper;

import com.bebopze.cloud.framework.model.product.entity.ProductDO;

public interface ProductDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProductDO record);

    int insertSelective(ProductDO record);

    ProductDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductDO record);

    int updateByPrimaryKeyWithBLOBs(ProductDO record);

    int updateByPrimaryKey(ProductDO record);
}