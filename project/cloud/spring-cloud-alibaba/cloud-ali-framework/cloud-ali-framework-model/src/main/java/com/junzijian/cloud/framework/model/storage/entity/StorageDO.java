package com.junzijian.cloud.framework.model.storage.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StorageDO implements Serializable {

    private Long id;

    private Long productId;

    private String name;

    private Integer num;

    private Byte status;

    private Long version;

    private Date gmtCreate;

    private Date gmtModify;
}