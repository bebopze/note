package com.bebopze.jdk.frame.ratelimiter;

import com.bebopze.jdk.frame.ratelimiter.alg.RateLimitAlg;
import com.bebopze.jdk.frame.ratelimiter.datasource.FileRuleConfigSource;
import com.bebopze.jdk.frame.ratelimiter.datasource.RuleConfigSource;
import com.bebopze.jdk.frame.ratelimiter.rule.RateLimitRule;
import com.bebopze.jdk.frame.ratelimiter.rule.RuleConfig;
import com.bebopze.jdk.frame.ratelimiter.rule.TrieRateLimitRule;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 1、限流框架
 *
 * @author bebopze
 * @date 2020/8/17
 */
@Slf4j
public class RateLimiter {


// 重构后：         - 基于接口，非基于实现

//    com.bebopze.ratelimiter
//          - RateLimiter(有所修改)
//
//    com.bebopze.ratelimiter.rule
//          - ApiLimit(不变)
//          - RuleConfig(不变)
//          - RateLimitRule(抽象接口)
//          - TrieRateLimitRule(实现类，就是重构前的RateLimitRule）
//
//    com.bebopze.ratelimiter.rule.parser
//            - RuleConfigParser(抽象接口)
//          - YamlRuleConfigParser(Yaml格式配置文件解析类)
//          - JsonRuleConfigParser(Json格式配置文件解析类)
//
//    com.bebopze.ratelimiter.rule.datasource
//          - RuleConfigSource(抽象接口)
//          - FileRuleConfigSource(基于本地文件的配置类)
//
//    com.bebopze.ratelimiter.alg
//          - RateLimitAlg(抽象接口)
//          - FixedTimeWinRateLimitAlg(实现类，就是重构前的RateLimitAlg)


    /**
     * 为每个api在内存中存储限流计数器
     */
    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();

    private RateLimitRule rule;


    public RateLimiter() {
        //改动主要在这里：调用RuleConfigSource类来实现配置加载
        RuleConfigSource configSource = new FileRuleConfigSource();
        RuleConfig ruleConfig = configSource.load();
        this.rule = new TrieRateLimitRule(ruleConfig);
    }

    public boolean limit(String appId, String url) {

        //...代码不变...

        return true;
    }
}