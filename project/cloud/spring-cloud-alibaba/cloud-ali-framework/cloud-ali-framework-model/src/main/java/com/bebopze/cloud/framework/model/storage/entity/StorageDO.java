package com.bebopze.cloud.framework.model.storage.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StorageDO implements Serializable {

    private Long id;

    private Long productId;

    private String productName;

    private Integer inventory;

    private Byte status;

    private Long version;

    private Date gmtCreate;

    private Date gmtModify;
}