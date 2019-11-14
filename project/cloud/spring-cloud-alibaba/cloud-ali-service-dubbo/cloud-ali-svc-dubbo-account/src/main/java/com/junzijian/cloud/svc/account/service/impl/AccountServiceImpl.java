package com.junzijian.cloud.svc.account.service.impl;

import com.junzijian.cloud.client.account.AccountClient;
import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.cloud.svc.account.mapper.AccountDOMapper;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountClient {

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
        log.info("decrAmount begin        >>>     xid : {}", RootContext.getXID());

        accountDOMapper.decrAmount(userId, decrAmount);

        log.info("decrAmount end        >>>     xid : {}", RootContext.getXID());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrAmount(@NotNull(message = "userId不能为空") Long userId,
                           @NotNull(message = "incrAmount不能为空") BigDecimal incrAmount) {
        log.info("incrAmount begin        >>>     xid : {}", RootContext.getXID());

        accountDOMapper.incrAmount(userId, incrAmount);

        log.info("incrAmount end        >>>     xid : {}", RootContext.getXID());
    }
}
