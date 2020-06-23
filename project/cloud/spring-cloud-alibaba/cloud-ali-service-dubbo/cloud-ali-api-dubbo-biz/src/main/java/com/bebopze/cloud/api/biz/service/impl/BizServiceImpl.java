package com.bebopze.cloud.api.biz.service.impl;

import com.bebopze.cloud.api.biz.service.BizService;
import com.bebopze.cloud.client.account.AccountClient;
import com.bebopze.cloud.client.order.OrderClient;
import com.bebopze.cloud.client.storage.StorageClient;
import com.bebopze.cloud.framework.model.biz.param.BuyOrderParam;
import com.bebopze.cloud.framework.model.order.param.OrderParam;
import com.bebopze.framework.common.exception.CustomException;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author bebopze
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
    @GlobalTransactional(timeoutMills = 30000, name = "cloud-ali-api-dubbo-biz--buy")
    public Long buy(BuyOrderParam param) {
        log.info("buy begin        >>>     xid : {}", RootContext.getXID());

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

        if (param.isException()) {
            throw new CustomException("下单失败了...");
        }
        log.info("buy end        >>>     xid : {}", RootContext.getXID());

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