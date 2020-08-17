package com.bebopze.jdk.frame.ratelimiter.rule;

import com.bebopze.jdk.frame.ratelimiter.rule.ApiLimit;
import com.bebopze.jdk.frame.ratelimiter.rule.RuleConfig;

/**
 * @author bebopze
 * @date 2020/8/17
 */
public abstract class RateLimitRule {


    public RateLimitRule(RuleConfig ruleConfig) {
        // ...
    }

    public abstract ApiLimit getLimit(String appId, String api);
}
