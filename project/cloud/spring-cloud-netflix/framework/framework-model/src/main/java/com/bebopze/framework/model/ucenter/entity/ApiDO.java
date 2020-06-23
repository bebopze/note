package com.bebopze.framework.model.ucenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ApiDO {

    private Long id;

    private String name;

    private String url;

    private Byte method;

    private Date gmtCreate;

    private Date gmtModified;
}