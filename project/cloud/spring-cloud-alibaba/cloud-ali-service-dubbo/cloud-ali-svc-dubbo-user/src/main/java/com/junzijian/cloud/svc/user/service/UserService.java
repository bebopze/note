package com.junzijian.cloud.svc.user.service;

import com.junzijian.cloud.framework.model.user.param.UserParam;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface UserService {

    Long save(@Valid UserParam param);
}
