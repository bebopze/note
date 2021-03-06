package com.bebopze.cloud.api.biz.service.impl;

import com.bebopze.cloud.api.biz.service.BizService;
import com.bebopze.cloud.client.account.AccountClient;
import com.bebopze.cloud.client.order.OrderClient;
import com.bebopze.cloud.client.storage.StorageClient;
import com.bebopze.cloud.client.user.UserClient;
import com.bebopze.cloud.framework.model.biz.param.BuyOrderParam;
import com.bebopze.cloud.framework.model.order.param.OrderParam;
import com.bebopze.framework.common.model.response.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author bebopze
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
    public Long buy(BuyOrderParam param) {

        return doBuy(param);
    }

    @Override
    public Long buyCloudDubbo(BuyOrderParam param) {

        return doBuy(param);
    }


    /**
     * do buy
     *
     * @param param
     * @return
     */
    private Long doBuy(BuyOrderParam param) {

        Long productId = param.getProductId();
        Integer num = param.getNum();
        BigDecimal price = param.getPrice();

        // 减库存
        ResultBean<Void> storageResult = storageClient.decrInventory(productId, num);

        // 扣款
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(num));
        ResultBean<Void> accountResult = accountClient.decrAmount(productId, totalPrice);

        // 下单
        OrderParam order = convertOrderParam(param, totalPrice);
        ResultBean<Long> orderResult = orderClient.save(order);

        return orderResult.getData();
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