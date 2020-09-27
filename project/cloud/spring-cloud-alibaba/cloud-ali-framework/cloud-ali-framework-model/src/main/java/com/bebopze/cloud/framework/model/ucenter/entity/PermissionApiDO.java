package com.bebopze.cloud.framework.model.ucenter.entity;

import java.util.Date;

public class PermissionApiDO extends PermissionApiDOKey {
    private Date gmtCreate;

    private Date gmtModified;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}