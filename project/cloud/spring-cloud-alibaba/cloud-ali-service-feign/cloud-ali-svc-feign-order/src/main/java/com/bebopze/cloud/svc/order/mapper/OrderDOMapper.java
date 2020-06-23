package com.bebopze.cloud.svc.order.mapper;

import com.bebopze.cloud.framework.model.order.entity.OrderDO;

public interface OrderDOMapper {

    int deleteByPrimaryKey(Long id);

    int insert(OrderDO record);

    int insertSelective(OrderDO record);

    OrderDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);
}