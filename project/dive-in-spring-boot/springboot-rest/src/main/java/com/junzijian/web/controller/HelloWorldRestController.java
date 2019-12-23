package com.junzijian.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorld {@link RestController} 实现
 *
 * @author junzijian
 * @since 2018/5/27
 */
@RestController
public class HelloWorldRestController {

    @GetMapping(value = "/hello-world")
    public String helloWorld(@RequestParam(required = false) String message) {
        return "Hello,World! : " + message;
    }

//    @CrossOrigin("*")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
