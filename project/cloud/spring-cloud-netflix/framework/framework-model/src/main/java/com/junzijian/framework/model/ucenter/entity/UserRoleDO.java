package com.junzijian.framework.model.ucenter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserRoleDO extends UserRoleDOKey {

    private Date gmtCreate;

    private Date gmtModified;
}