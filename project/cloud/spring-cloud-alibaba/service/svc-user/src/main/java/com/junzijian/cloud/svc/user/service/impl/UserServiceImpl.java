package com.junzijian.cloud.svc.user.service.impl;

import com.junzijian.cloud.framework.model.user.param.UserParam;
import com.junzijian.cloud.svc.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(@Valid UserParam param) {
        return null;
    }
}
