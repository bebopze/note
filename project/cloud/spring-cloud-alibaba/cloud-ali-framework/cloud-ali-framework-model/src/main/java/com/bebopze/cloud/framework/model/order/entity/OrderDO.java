package com.bebopze.cloud.framework.model.order.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDO implements Serializable {

    private Long id;

    private Long userId;

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productNum;

    private BigDecimal productTotalPrice;

    private Byte status;

    private Long version;

    private Date gmtCreate;

    private Date gmtModify;
}