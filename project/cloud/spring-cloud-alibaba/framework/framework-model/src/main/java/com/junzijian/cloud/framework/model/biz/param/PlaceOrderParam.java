package com.junzijian.cloud.framework.model.biz.param;

import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import lombok.Data;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Data
public class PlaceOrderParam {

    private OrderParam order;

    private AccountParam account;

    private StorageParam storage;

}
