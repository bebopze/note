package com.bebopze.jdk.frame.ratelimiter.rule;

import com.bebopze.jdk.frame.ratelimiter.rule.ApiLimit;
import lombok.Data;

import java.util.List;

/**
 * @author bebopze
 * @date 2020/8/17
 */
public class RuleConfig {
    private List<AppRuleConfig> configs;

    public List<AppRuleConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AppRuleConfig> configs) {
        this.configs = configs;
    }


    @Data
    public static class AppRuleConfig {
        private String appId;
        private List<ApiLimit> limits;


        public AppRuleConfig() {
        }

        public AppRuleConfig(String appId, List<ApiLimit> limits) {
            this.appId = appId;
            this.limits = limits;
        }
    }
}
