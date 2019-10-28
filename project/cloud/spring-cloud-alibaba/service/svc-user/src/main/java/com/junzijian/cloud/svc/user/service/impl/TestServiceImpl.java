package com.junzijian.cloud.svc.user.service.impl;

import com.junzijian.cloud.svc.user.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String sayHello(@NotEmpty String... name) {
        return "hello : " + Arrays.toString(name);
    }
}
