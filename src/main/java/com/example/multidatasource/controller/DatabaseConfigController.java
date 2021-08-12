package com.example.multidatasource.controller;

import com.example.multidatasource.config.DataSourceComponent;
import com.example.multidatasource.config.DataSourceContext;
import com.example.multidatasource.entity.DatabaseConfig;
import com.example.multidatasource.service.DatabaseConfigService;
import com.example.multidatasource.service.DynamicTableService;
import com.example.multidatasource.utils.DataSourceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/11/21 11:26 PM
 */
@RestController
@RequestMapping("/config")
@Slf4j
public class DatabaseConfigController {
    @Autowired
    DatabaseConfigService databaseConfigService;
    @Autowired
    DynamicTableService dynamicTableService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("test")
    public List<DatabaseConfig> findAll(){
        return databaseConfigService.findList();
    }

    @PostMapping("add")
    public Boolean save(DatabaseConfig info){
        log.info("info:{}", info);
        boolean rt = databaseConfigService.save(info);
        log.info("info:{}", info);
        DataSourceUtil.addDataSource(info);
        DataSourceUtil.flushDataSource();
        return rt;
    }

    @GetMapping("get")
    public void get(String key){
        DataSourceContext.setDataSource(key);
        log.info("切换数据源：{}", DataSourceContext.getDataSource());
        String sql = "select * from user_info limit 1";
        try {
            HikariDataSource dataSource = (HikariDataSource) DataSourceUtil.dataSourceMap.get(key);
            Statement statement = dataSource.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                log.info("rs:{}", rs.getString("phone"));
            }
            rs.close();
            statement.close();
        }catch (Exception e){
            log.error("异常 ", e);
        }
    }

    @GetMapping("test1")
    public void testNew(){
        DataSourceContext.setDataSource("cb5440292a3b2b1d7d8c742adbef3b3c");
        log.info("切换数据源：{}", DataSourceContext.getDataSource());
        JdbcTemplate jdbcTemplate = new JdbcTemplate((HikariDataSource) DataSourceUtil.dataSourceMap.get(DataSourceContext.getDataSource()));
        String key1 = dynamicTableService.getPhone(jdbcTemplate);
        log.info("数据源1 ：{}", key1);

        DataSourceContext.setDataSource("4a8d34c34a8ec0778e533b2bd6e391ce");
        log.info("切换数据源：{}", DataSourceContext.getDataSource());
        jdbcTemplate = new JdbcTemplate((HikariDataSource) DataSourceUtil.dataSourceMap.get(DataSourceContext.getDataSource()));
        String key2 = dynamicTableService.getPhone(jdbcTemplate);
        log.info("数据源2：{}", key2);
    }
}
