package com.junzijian.service.ucenter.service;

import com.junzijian.framework.model.ucenter.entity.UserDO;

/**
 * @author bebop
 * @date 2019/5/5
 */
public interface UserCenterService {

    UserDO getUserByUsername(String username);
}
