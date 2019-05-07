package com.junzijian.framework.model.ucenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PermissionApiDO extends PermissionApiDOKey {

    private Date gmtCreate;

    private Date gmtModified;

}