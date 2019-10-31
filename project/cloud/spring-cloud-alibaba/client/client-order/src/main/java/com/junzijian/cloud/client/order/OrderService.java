package com.junzijian.cloud.client.order;

import com.junzijian.cloud.framework.model.order.entity.OrderDO;
import com.junzijian.cloud.framework.model.order.param.OrderParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author junzijian
 * @date 2019/10/31
 */
public interface OrderService {

    /**
     * 创建/修改
     *
     * @param param
     * @return
     */
    Long save(@Valid OrderParam param);

    OrderDO detail(@NotNull Long orderId);
}
