package com.bebopze.cloud.svc.account.service.impl;

import com.bebopze.cloud.framework.model.account.param.AccountParam;
import com.bebopze.cloud.svc.account.mapper.AccountDOMapper;
import com.bebopze.cloud.svc.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author bebopze
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrAmount(@NotNull(message = "userId不能为空") Long userId,
                           @NotNull(message = "decrAmount不能为空") BigDecimal decrAmount) {


        accountDOMapper.decrAmount(userId, decrAmount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrAmount(@NotNull(message = "userId不能为空") Long userId,
                           @NotNull(message = "incrAmount不能为空") BigDecimal incrAmount) {

        accountDOMapper.incrAmount(userId, incrAmount);
    }
}
