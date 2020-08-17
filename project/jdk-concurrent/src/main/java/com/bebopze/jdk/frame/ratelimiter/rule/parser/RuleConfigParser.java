package com.bebopze.jdk.frame.ratelimiter.rule.parser;

import com.bebopze.jdk.frame.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

/**
 * @author bebopze
 * @date 2020/8/17
 */


//com.xzg.ratelimiter.rule.parser
//        --RuleConfigParser(抽象接口)
//        --YamlRuleConfigParser(Yaml格式配置文件解析类)
//        --JsonRuleConfigParser(Json格式配置文件解析类)
//        com.xzg.ratelimiter.rule.datasource
//        --RuleConfigSource(抽象接口)
//        --FileRuleConfigSource(基于本地文件的配置类)

public interface RuleConfigParser {

    RuleConfig parse(String configText);

    RuleConfig parse(InputStream in);
}
