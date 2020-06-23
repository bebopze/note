package com.bebopze.cloud.client.order.dubbo;

import com.bebopze.cloud.framework.model.order.entity.OrderDO;
import com.bebopze.cloud.framework.model.order.param.OrderParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author bebopze
 * @date 2019/10/31
 */
public interface OrderDubboService {

    /**
     * 创建/修改
     *
     * @param param
     * @return
     */
    Long save(@Valid OrderParam param);

    OrderDO detail(@NotNull Long orderId);
}
