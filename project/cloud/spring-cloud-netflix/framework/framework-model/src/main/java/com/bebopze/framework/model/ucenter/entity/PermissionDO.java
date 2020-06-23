package com.bebopze.framework.model.ucenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PermissionDO {

    private Long id;

    private String name;

    private Date gmtCreate;

    private Date gmtModified;
}