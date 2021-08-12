package com.example.multidatasource.config;

import com.example.multidatasource.entity.DatabaseConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/11/21 11:46 PM
 */
@Slf4j
@Configuration
public class DataSourceComponent implements EnvironmentAware {
    private Environment evn;

    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        log.info("创建masterDataSource");
        //DruidDataSource druidDataSource = new DruidDataSource();
        DatabaseConfig config = new DatabaseConfig();
        config.setId("master");
        config.setName("master");
        config.setUrl(evn.getProperty("spring.datasource.url"));
        config.setUserName(evn.getProperty("spring.datasource.username"));
        config.setPassword(evn.getProperty("spring.datasource.password"));
        HikariDataSource hikariDataSource = DataSourceProvider.create(config);
        return hikariDataSource;
    }

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        MultiRouteDataSource myDynamicDataSource = new MultiRouteDataSource();

        // 配置多数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        //targetDataSources.put("slave", slaveDataSource());
        myDynamicDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源，在动态添加数据源的时候，就可以不再添加此数据源了
        // myDynamicDataSource.setDefaultTargetDataSource(masterDataSource());

        //dynamicDataSource.setDefaultTargetDataSource(slaveDataSource());
        return myDynamicDataSource;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.evn = environment;
    }
}
