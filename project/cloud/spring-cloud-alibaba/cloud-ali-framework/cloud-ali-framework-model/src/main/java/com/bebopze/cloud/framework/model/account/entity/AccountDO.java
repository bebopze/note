package com.bebopze.cloud.framework.model.account.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountDO implements Serializable {

    private Long id;

    private Long userId;

    private BigDecimal amount;

    private Long version;

    private Date gmtCreate;

    private Date gmtModify;
}