package com.bebopze.service.ucenter.service.impl;

import com.bebopze.framework.model.ucenter.entity.UserDO;
import com.bebopze.service.ucenter.mapper.UserRepository;
import com.bebopze.service.ucenter.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bebopze
 * @date 2019/5/5
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDO getUserByUsername(String username) {
        UserDO userDO = userRepository.findByName(username);
        return userDO;
    }
}
