package com.bebopze.cloud.client.order;

import com.bebopze.cloud.framework.model.order.entity.OrderDO;
import com.bebopze.cloud.framework.model.order.param.OrderParam;

/**
 * @author bebopze
 * @date 2019/10/28
 */
public interface OrderClient {

    Long save(OrderParam param);

    OrderDO detail(Long orderId);
}
