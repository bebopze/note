package com.junzijian.cloud.svc.biz.service;

import com.junzijian.cloud.framework.model.biz.param.PlaceOrderParam;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface BizService {

    /**
     * 下单
     *
     * @param param
     * @return 订单号
     */
    Long placeOrder(PlaceOrderParam param);
}
