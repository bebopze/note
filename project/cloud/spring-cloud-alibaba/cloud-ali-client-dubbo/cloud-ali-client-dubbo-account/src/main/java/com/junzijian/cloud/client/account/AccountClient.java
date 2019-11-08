package com.junzijian.cloud.client.account;

import com.junzijian.cloud.framework.model.account.param.AccountParam;

import java.math.BigDecimal;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface AccountClient {

    void save(AccountParam param);

    void decrAmount(Long userId, BigDecimal decrAmount);

    void incrAmount(Long userId, BigDecimal incrAmount);
}
