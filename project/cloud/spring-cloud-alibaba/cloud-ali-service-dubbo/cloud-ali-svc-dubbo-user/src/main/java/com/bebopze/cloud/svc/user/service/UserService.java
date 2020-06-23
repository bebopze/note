package com.bebopze.cloud.svc.user.service;

import com.bebopze.cloud.framework.model.user.param.UserParam;

import javax.validation.Valid;

/**
 * @author bebopze
 * @date 2019/10/28
 */
public interface UserService {

    Long save(@Valid UserParam param);
}
