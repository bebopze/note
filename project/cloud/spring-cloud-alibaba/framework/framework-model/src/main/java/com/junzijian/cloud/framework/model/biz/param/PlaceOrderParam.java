package com.junzijian.cloud.framework.model.biz.param;

import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.cloud.framework.model.product.param.ProductParam;
import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.cloud.framework.model.user.param.UserParam;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Data
public class PlaceOrderParam {

    private UserParam user;

    private ProductParam product;

    private StorageParam storage;

    private AccountParam account;

    private OrderParam order;


    private Long userId;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private Integer num;
}
