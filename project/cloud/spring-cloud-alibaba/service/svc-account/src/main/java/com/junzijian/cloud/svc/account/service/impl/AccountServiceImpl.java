package com.junzijian.cloud.svc.account.service.impl;

import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.cloud.svc.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AccountParam param) {

    }
}
