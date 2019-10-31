package com.junzijian.cloud.svc.biz.service.impl;

import com.junzijian.cloud.client.account.AccountClient;
import com.junzijian.cloud.client.order.OrderClient;
import com.junzijian.cloud.client.storage.StorageClient;
import com.junzijian.cloud.client.user.UserClient;
import com.junzijian.cloud.framework.model.biz.param.PlaceOrderParam;
import com.junzijian.cloud.svc.biz.service.BizService;
import com.junzijian.framework.common.model.response.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class BizServiceImpl implements BizService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private AccountClient accountClient;


    @Override
    public Long placeOrder(PlaceOrderParam param) {

        ResultBean<Long> save = storageClient.save(param.getStorage());

        ResultBean<Void> save1 = accountClient.save(param.getAccount());

        ResultBean<Long> resultBean = orderClient.save(param.getOrder());

        return resultBean.getData();
    }
}