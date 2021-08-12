package com.example.multidatasource.utils;

import com.example.multidatasource.config.DataSourceProvider;
import com.example.multidatasource.config.MultiRouteDataSource;
import com.example.multidatasource.entity.DatabaseConfig;
import com.example.multidatasource.service.DatabaseConfigService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/12/21 2:22 PM
 */
@Slf4j
public class DataSourceUtil {
    public static final Map<Object, Object> dataSourceMap = new HashMap<>();

    public static void initDataSource() {
        //获取masterDataSource
        HikariDataSource masterDataSource = (HikariDataSource) SpringContextUtil.getBean("masterDataSource");
        addDataSource("master", masterDataSource);
        //初始化其它数据源
        initOthersDataSource();
        //刷新数据源
        flushDataSource();
    }

    public static void flushDataSource() {
        //获取spring管理的dynamicDataSource
        MultiRouteDataSource myDynamicDataSource = (MultiRouteDataSource) SpringContextUtil.getBean("dynamicDataSource");
        //将数据源设置到 targetDataSources
        log.info("注册数据源成功，一共注册{}个数据源", dataSourceMap.keySet().size());
        myDynamicDataSource.setTargetDataSources(dataSourceMap);
        //将 targetDataSources 中的连接信息放入 resolvedDataSources 管理
        myDynamicDataSource.afterPropertiesSet();
    }

    public static void addDataSource(String key, HikariDataSource masterDataSource) {
        dataSourceMap.put(key, masterDataSource);
    }

    public static void addDataSource(DatabaseConfig config) {
        HikariDataSource hikariDataSource = DataSourceProvider.create(config);
        dataSourceMap.put(config.getId(), hikariDataSource);
    }

    private static void initOthersDataSource() {
        //在此处可以查询出所有的数据源（例如，配置文件，数据库）然后添加
        Connection conn = null;
        try {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSourceMap.get("master");
            conn = hikariDataSource.getConnection();
            String sql = "select * from database_config ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                DatabaseConfig config = new DatabaseConfig();
                String key = rs.getString("id");
                config.setId(key);
                config.setUrl(rs.getString("url"));
                config.setUserName(rs.getString("user_name"));
                config.setPassword(rs.getString("password"));
                // 绑定参数
                addDataSource(config);
            }
            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
