package com.bebopze.cloud.svc.user.service.impl;

import com.bebopze.cloud.framework.model.user.param.UserParam;
import com.bebopze.cloud.svc.user.mapper.UserDOMapper;
import com.bebopze.cloud.svc.user.service.UserService;
import com.bebopze.framework.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * @author bebopze
 * @date 2019/10/28
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserDOMapper userDOMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(@Valid UserParam param) {

        Long id = param.getId();

        if (id == null) {

            param.setId(idWorker.nextId());

            // insert
            userDOMapper.insertSelective(param);

        } else {

            // update
            userDOMapper.updateByPrimaryKeySelective(param);
        }

        return param.getId();
    }
}
