package com.bebopze.web.controller;

import com.bebopze.web.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User {@link RestController}
 *
 * @author bebopze
 * @since 2018/5/27
 */
@RestController
public class UserRestController {

    @PostMapping(value = "/echo/user",
            consumes = "application/*;charset=UTF-8",
            produces = "application/json;charset=UTF-8")
    public User user(@RequestBody User user) {
        return user;
    }

}
