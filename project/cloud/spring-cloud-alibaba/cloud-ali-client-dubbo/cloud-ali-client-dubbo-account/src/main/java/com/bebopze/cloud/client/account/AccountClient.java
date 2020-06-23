package com.bebopze.cloud.client.account;

import com.bebopze.cloud.framework.model.account.param.AccountParam;

import java.math.BigDecimal;

/**
 * @author bebopze
 * @date 2019/10/28
 */
public interface AccountClient {

    void save(AccountParam param);

    void decrAmount(Long userId, BigDecimal decrAmount);

    void incrAmount(Long userId, BigDecimal incrAmount);
}
