package com.junzijian.cloud.api.biz.service;

import com.junzijian.cloud.framework.model.biz.param.BuyOrderParam;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface BizService {

    /**
     * 下单   cloud
     *
     * @param param
     * @return 订单号
     */
    Long buy(BuyOrderParam param);

    /**
     * 下单   cloud-dubbo
     *
     * @param param
     * @return
     */
    Long buyCloudDubbo(@Valid BuyOrderParam param);
}
