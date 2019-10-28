package com.junzijian.cloud.svc.user.service;

import javax.validation.constraints.NotEmpty;

/**
 * @author junzijian
 * @date 2019/10/28
 */
public interface TestService {

    String sayHello(@NotEmpty String... name);
}
