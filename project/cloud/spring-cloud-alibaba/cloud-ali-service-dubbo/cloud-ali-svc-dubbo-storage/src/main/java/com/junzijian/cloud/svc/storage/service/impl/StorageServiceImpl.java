package com.junzijian.cloud.svc.storage.service.impl;

import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.cloud.svc.storage.mapper.StorageDOMapper;
import com.junzijian.cloud.svc.storage.service.StorageService;
import com.junzijian.framework.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service(protocol = {"dubbo", "rest"})
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrInventory(@NotNull(message = "productId不能为空") Long productId,
                              @NotNull(message = "decrNum不能为空") int decrNum) {

        storageDOMapper.decrInventory(productId, decrNum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrInventory(@NotNull(message = "productId不能为空") Long productId,
                              @NotNull(message = "incrNum不能为空") int incrNum) {

        storageDOMapper.incrInventory(productId, incrNum);
    }
}
