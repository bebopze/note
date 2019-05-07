package com.junzijian.service.ucenter.service.impl;

import com.junzijian.framework.model.ucenter.entity.UserDO;
import com.junzijian.service.ucenter.mapper.UserRepository;
import com.junzijian.service.ucenter.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuzhe
 * @date 2019/5/5
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDO getUserByUsername(String username) {
        UserDO userDO = userRepository.findByUsername(username);
        return userDO;
    }
}
