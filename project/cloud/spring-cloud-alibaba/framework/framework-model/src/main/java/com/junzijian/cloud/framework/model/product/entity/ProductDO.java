package com.junzijian.cloud.framework.model.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductDO implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private Date saleTime;

    private Byte isSale;

    private Byte isHot;

    private Byte isNew;

    private String info;

    private Byte status;

    private Date gmtCreate;

    private Date gmtModify;
}