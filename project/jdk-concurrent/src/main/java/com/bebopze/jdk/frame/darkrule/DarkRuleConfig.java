package com.bebopze.jdk.frame.darkrule;

import lombok.Data;

import java.util.List;

/**
 * @author bebopze
 * @date 2020/8/17
 */
public class DarkRuleConfig {

    private List<DarkFeatureConfig> features;


    public List<DarkFeatureConfig> getFeatures() {
        return this.features;
    }

    public void setFeatures(List<DarkFeatureConfig> features) {
        this.features = features;
    }


    @Data
    public static class DarkFeatureConfig {
        private String key;
        private boolean enabled;
        private String rule;
    }
}