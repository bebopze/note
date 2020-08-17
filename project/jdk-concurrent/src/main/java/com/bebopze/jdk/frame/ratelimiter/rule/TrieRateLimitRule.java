package com.bebopze.jdk.frame.ratelimiter.rule;

import com.bebopze.jdk.frame.ratelimiter.rule.ApiLimit;
import com.bebopze.jdk.frame.ratelimiter.rule.RateLimitRule;
import com.bebopze.jdk.frame.ratelimiter.rule.RuleConfig;

/**
 * @author bebopze
 * @date 2020/8/17
 */
public class TrieRateLimitRule extends RateLimitRule {


    public TrieRateLimitRule(RuleConfig ruleConfig) {
        super(ruleConfig);
        // ...
    }

    @Override
    public ApiLimit getLimit(String appId, String api) {
        //...

        return null;
    }
}
