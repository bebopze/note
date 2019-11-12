package com.junzijian.cloud.api.biz.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Seata DataSource Proxy Config
 * -
 * -
 * Seata 通过 代理数据源 的方式实现分支事务
 * -
 * - MyBatis 和 JPA 都需要注入 io.seata.rm.datasource.DataSourceProxy
 * -
 * - 不同的是：MyBatis 还需要额外注入 org.apache.ibatis.session.SqlSessionFactory
 * -
 * -
 * - @see https://github.com/seata/seata-samples/blob/master/doc/quick-integration-with-spring-cloud.md
 * - @see https://gitee.com/junzijian/seata-samples/blob/master/doc/quick-integration-with-spring-cloud.md
 *
 * @author junzijian
 * @date 2019/11/12
 */
@Configuration
public class DataSourceProxyConfig {

    /**
     * Druid DataSource
     *
     * @return
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * seata proxy DataSource
     *
     * @param dataSource Druid DataSource
     * @return
     */
    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    /**
     * SqlSessionFactory
     * --
     * -- MyBatis 还需要额外注入 org.apache.ibatis.session.SqlSessionFactory
     * --
     * -- JPA 注释掉即可
     *
     * @param dataSourceProxy seata proxy DataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        return sqlSessionFactoryBean.getObject();
    }
}
