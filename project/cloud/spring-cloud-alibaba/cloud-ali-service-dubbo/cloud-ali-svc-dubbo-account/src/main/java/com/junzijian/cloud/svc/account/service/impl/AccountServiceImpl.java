package com.junzijian.cloud.svc.account.service.impl;

import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.cloud.svc.account.mapper.AccountDOMapper;
import com.junzijian.cloud.svc.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDOMapper accountDOMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(AccountParam param) {

        Long id = param.getId();

        if (id == null) {

            // insert
            accountDOMapper.insertSelective(param);

        } else {

            // update
            accountDOMapper.updateByPrimaryKeySelective(param);
        }
    }
}
