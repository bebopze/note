package com.bebopze.jdk.frame.ratelimiter.datasource;

import com.bebopze.jdk.frame.ratelimiter.rule.RuleConfig;

/**
 * @author bebopze
 * @date 2020/8/17
 */
public interface RuleConfigSource {

    RuleConfig load();
}
