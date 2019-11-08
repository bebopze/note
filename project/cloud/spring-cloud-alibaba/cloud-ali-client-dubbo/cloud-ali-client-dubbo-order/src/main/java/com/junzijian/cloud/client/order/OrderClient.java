package com.junzijian.cloud.client.order;

import com.junzijian.cloud.framework.model.order.entity.OrderDO;
import com.junzijian.cloud.framework.model.order.param.OrderParam;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface OrderClient {

    Long save(OrderParam param);

    OrderDO detail(Long orderId);
}
