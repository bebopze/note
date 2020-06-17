package com.junzijian.framework.model;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bebopze
 * @date 2018/1/11
 */
public class GeneratorSqlmap {

    public void generator() throws Exception {

        List<String> warnings = new ArrayList<>();

        // 指定逆向工程配置文件
        String basePath = System.getProperty("user.dir");
        String user_home = System.getProperty("user.home");

        String path = basePath + "/project/cloud/spring-cloud-alibaba/framework/framework-model/src/main/resources/generatorConfig.xml";
        File configFile = new File(path);

        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        myBatisGenerator.generate(null);
    }

    public static void main(String[] args) {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
