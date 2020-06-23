package com.bebopze.cloud.framework.model.biz.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author bebopze
 * @date 2019/10/28
 */
@Data
public class BuyOrderParam {

//    private UserParam user;
//
//    private ProductParam product;
//
//    private StorageParam storage;
//
//    private AccountParam account;
//
//    private OrderParam order;


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

    private boolean exception = false;
}
