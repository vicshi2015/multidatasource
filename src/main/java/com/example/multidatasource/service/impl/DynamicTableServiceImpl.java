package com.example.multidatasource.service.impl;

import com.example.multidatasource.service.DynamicTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/12/21 2:59 PM
 */
@Service
public class DynamicTableServiceImpl implements DynamicTableService {
    @Override
    public String getPhone(JdbcTemplate jdbcTemplate) {
        String sql = "select phone from user_info limit 1";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}
