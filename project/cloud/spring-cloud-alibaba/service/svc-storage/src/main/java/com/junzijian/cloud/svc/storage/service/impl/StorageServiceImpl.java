package com.junzijian.cloud.svc.storage.service.impl;

import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.cloud.svc.storage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {


    @Override
    public Long save(@Valid StorageParam param) {

        return null;
    }
}
