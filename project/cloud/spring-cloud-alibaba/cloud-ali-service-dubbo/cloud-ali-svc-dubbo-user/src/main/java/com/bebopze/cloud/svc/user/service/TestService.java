package com.bebopze.cloud.svc.user.service;

import javax.validation.constraints.NotEmpty;

/**
 * @author bebopze
 * @date 2019/10/28
 */
public interface TestService {

    String sayHello(@NotEmpty String... name);
}
