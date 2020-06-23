package com.bebopze.service.ucenter.service;

import com.bebopze.framework.model.ucenter.entity.UserDO;

/**
 * @author bebopze
 * @date 2019/5/5
 */
public interface UserCenterService {

    UserDO getUserByUsername(String username);
}
