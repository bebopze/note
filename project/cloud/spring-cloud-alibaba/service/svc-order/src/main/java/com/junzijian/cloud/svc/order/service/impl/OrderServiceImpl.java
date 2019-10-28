package com.junzijian.cloud.svc.order.service.impl;

import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.cloud.svc.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(@Valid OrderParam param) {

        return null;
    }

    @Override
    public Object detail(@NotNull Long orderId) {
        return null;
    }
}
