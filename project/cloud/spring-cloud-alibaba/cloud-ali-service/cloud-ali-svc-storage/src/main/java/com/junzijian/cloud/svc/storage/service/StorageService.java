package com.junzijian.cloud.svc.storage.service;

import com.junzijian.cloud.framework.model.storage.param.StorageParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface StorageService {

    Long save(@Valid StorageParam param);

    void decrInventory(@NotNull(message = "productId不能为空") Long productId,
                       @NotNull(message = "decrNum不能为空") int decrNum);

    void incrInventory(@NotNull(message = "productId不能为空") Long productId,
                       @NotNull(message = "incrNum不能为空") int incrNum);
}
