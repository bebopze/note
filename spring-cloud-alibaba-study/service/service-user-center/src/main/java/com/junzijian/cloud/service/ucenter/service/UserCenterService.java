package com.junzijian.cloud.service.ucenter.service;

import com.junzijian.framework.model.ucenter.entity.UserDO;

/**
 * @author liuzhe
 * @date 2019/5/5
 */
public interface UserCenterService {

    UserDO getUserByUsername(String username);
}
