package com.junzijian.cloud.api.biz.service.impl;

import com.junzijian.cloud.api.biz.service.BizService;
import com.junzijian.cloud.client.account.AccountClient;
import com.junzijian.cloud.client.order.OrderClient;
import com.junzijian.cloud.client.storage.StorageClient;
import com.junzijian.cloud.framework.model.biz.param.BuyOrderParam;
import com.junzijian.cloud.framework.model.order.param.OrderParam;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class BizServiceImpl implements BizService {

    @Reference
    private OrderClient orderClient;

    @Reference
    private StorageClient storageClient;

    @Reference
    private AccountClient accountClient;


    @Override
    @GlobalTransactional(timeoutMills = 30000, name = "jzj-cloud-dubbo-tx")
    public Long buy(BuyOrderParam param) {
        log.info("buy begin ... xid: " + RootContext.getXID());

        Long productId = param.getProductId();
        Integer num = param.getNum();
        BigDecimal price = param.getPrice();

        // 减库存
        storageClient.decrInventory(productId, num);

        // 扣款
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(num));
        accountClient.decrAmount(productId, totalPrice);

        // 下单
        OrderParam order = convertOrderParam(param, totalPrice);
        Long orderId = orderClient.save(order);

        log.info("buy end ... xid: " + RootContext.getXID());
        return orderId;
    }


    /**
     * convert Order Param
     *
     * @param param
     * @param totalPrice
     * @return
     */
    private OrderParam convertOrderParam(BuyOrderParam param, BigDecimal totalPrice) {

        OrderParam order = new OrderParam();
        order.setUserId(param.getUserId());
        order.setProductId(param.getProductId());
        order.setProductName(param.getProductName());
        order.setProductPrice(param.getPrice());
        order.setProductNum(param.getNum());
        order.setProductTotalPrice(totalPrice);

        return order;
    }
}