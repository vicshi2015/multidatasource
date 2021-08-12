package com.example.multidatasource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.multidatasource.dao.DatabaseConfigMapper;
import com.example.multidatasource.entity.DatabaseConfig;
import com.example.multidatasource.service.DatabaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/11/21 11:22 PM
 */
@Service
public class DatabaseConfigServiceImpl extends ServiceImpl<DatabaseConfigMapper, DatabaseConfig> implements DatabaseConfigService {
    @Autowired
    DatabaseConfigMapper databaseConfigMapper;

    @Override
    public List<DatabaseConfig> findList() {
        return databaseConfigMapper.selectList(null);
    }
}
