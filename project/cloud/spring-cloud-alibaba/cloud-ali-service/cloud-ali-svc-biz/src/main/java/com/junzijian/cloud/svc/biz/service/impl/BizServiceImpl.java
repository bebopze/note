package com.junzijian.cloud.svc.biz.service.impl;

import com.junzijian.cloud.client.account.AccountClient;
import com.junzijian.cloud.client.order.OrderClient;
import com.junzijian.cloud.client.order.dubbo.OrderDubboService;
import com.junzijian.cloud.client.storage.StorageClient;
import com.junzijian.cloud.client.user.UserClient;
import com.junzijian.cloud.framework.model.biz.param.PlaceOrderParam;
import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.cloud.svc.biz.service.BizService;
import com.junzijian.framework.common.model.response.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;

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

    @Reference
    private OrderDubboService orderDubboService;

    @Autowired
    private StorageClient storageClient;

    @Autowired
    private AccountClient accountClient;


    @Override
    public Long placeOrder(PlaceOrderParam param) {

        Long productId = param.getProductId();
        Integer num = param.getNum();
        BigDecimal price = param.getPrice();

        // 减库存
        ResultBean<Long> save = storageClient.minus(productId, num);

        // 扣款
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(num));
        ResultBean<Void> save1 = accountClient.minus(productId, totalPrice);

        // 下单
        OrderParam order = new OrderParam();
        order.setUserId(param.getUserId());
        order.setProductId(productId);
        order.setProductName(param.getProductName());
        order.setProductPrice(price);
        order.setProductNum(num);
        order.setProductTotalPrice(totalPrice);

        ResultBean<Long> resultBean = orderClient.save(order);

        return resultBean.getData();
    }

    @Override
    public Long placeOrderDubbo(@Valid PlaceOrderParam param) {

        Long orderId = orderDubboService.save(param.getOrder());

        return orderId;
    }
}