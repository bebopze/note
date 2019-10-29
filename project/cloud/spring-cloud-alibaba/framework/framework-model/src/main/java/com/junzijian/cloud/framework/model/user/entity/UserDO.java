package com.junzijian.cloud.framework.model.user.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author junzijian
 * @date 2019/10/29
 */
@Data
public class UserDO implements Serializable {

    private Long id;

    private String name;

    private String password;

    private String idCard;

    private String mobile;

    private String email;

    private String dingDing;

    private String machineId;

    private Byte notify;

    private Byte status;

    private String feature;

    private Date gmtCreate;

    private Date gmtModify;
}