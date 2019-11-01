package com.junzijian.cloud.svc.storage.service.impl;

import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.cloud.svc.storage.mapper.StorageDOMapper;
import com.junzijian.cloud.svc.storage.service.StorageService;
import com.junzijian.framework.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private StorageDOMapper storageDOMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(@Valid StorageParam param) {

        Long id = param.getId();

        if (id == null) {

            param.setId(idWorker.nextId());

            // insert
            storageDOMapper.insertSelective(param);

        } else {

            // update
            storageDOMapper.updateByPrimaryKeySelective(param);
        }

        return param.getId();
    }
}
