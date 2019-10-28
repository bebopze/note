package com.junzijian.cloud.svc.storage.service;

import com.junzijian.cloud.framework.model.storage.param.StorageParam;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface StorageService {

    Long save(@Valid StorageParam param);
}
