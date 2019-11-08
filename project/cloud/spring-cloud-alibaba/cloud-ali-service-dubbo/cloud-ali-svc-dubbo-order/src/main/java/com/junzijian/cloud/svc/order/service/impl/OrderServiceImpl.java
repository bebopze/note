package com.junzijian.cloud.svc.order.service.impl;

import com.junzijian.cloud.client.order.OrderClient;
import com.junzijian.cloud.framework.model.order.entity.OrderDO;
import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.cloud.svc.order.mapper.OrderDOMapper;
import com.junzijian.framework.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderClient {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderDOMapper orderDOMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(@Valid OrderParam param) {

        Long id = param.getId();

        if (id == null) {

            param.setId(idWorker.nextId());

            // insert
            orderDOMapper.insertSelective(param);

        } else {

            // update
            orderDOMapper.updateByPrimaryKeySelective(param);
        }

        return param.getId();
    }

    @Override
    public OrderDO detail(@NotNull Long orderId) {

        return orderDOMapper.selectByPrimaryKey(orderId);
    }
}
