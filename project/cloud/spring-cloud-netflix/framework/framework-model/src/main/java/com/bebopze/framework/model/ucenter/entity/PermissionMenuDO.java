package com.bebopze.framework.model.ucenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PermissionMenuDO extends PermissionMenuDOKey {

    private Date gmtCreate;

    private Date gmtModified;
}