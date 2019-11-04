package com.junzijian.cloud.svc.storage.service.impl;

import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.cloud.svc.storage.mapper.StorageDOMapper;
import com.junzijian.cloud.svc.storage.service.StorageService;
import com.junzijian.framework.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrInventory(@NotNull(message = "productId不能为空") Long productId,
                              @NotNull(message = "decrNum不能为空") int decrNum) {

        int count = storageDOMapper.decrInventory(productId, decrNum);
        Assert.isTrue(count > 0, "库存修改失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrInventory(@NotNull(message = "productId不能为空") Long productId,
                              @NotNull(message = "incrNum不能为空") int incrNum) {

        int count = storageDOMapper.incrInventory(productId, incrNum);
        Assert.isTrue(count > 0, "库存修改失败");
    }
}
