package com.example.multidatasource.config;

import com.example.multidatasource.entity.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author szs
 * @date 2019/3/14 15:07
 */
public class DataSourceProvider {
    public static HikariDataSource create(DatabaseConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(config.getUserName());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setJdbcUrl(config.getUrl());
//        hikariConfig.setDriverClassName(config.getDriverClassName());
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(hikariConfig);
    }
}
