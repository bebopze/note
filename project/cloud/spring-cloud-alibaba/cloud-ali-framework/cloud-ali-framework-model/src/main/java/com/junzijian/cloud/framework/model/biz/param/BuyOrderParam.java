package com.junzijian.cloud.framework.model.biz.param;

import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.cloud.framework.model.product.param.ProductParam;
import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.cloud.framework.model.user.param.UserParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Data
public class BuyOrderParam {

    private UserParam user;

    private ProductParam product;

    private StorageParam storage;

    private AccountParam account;

    private OrderParam order;


    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    @NotNull
    private String productName;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer num;
}
