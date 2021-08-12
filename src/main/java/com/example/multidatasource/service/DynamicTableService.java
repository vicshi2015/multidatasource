package com.example.multidatasource.service;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/12/21 2:58 PM
 */
public interface DynamicTableService {
    String getPhone(JdbcTemplate jdbcTemplate);
}
