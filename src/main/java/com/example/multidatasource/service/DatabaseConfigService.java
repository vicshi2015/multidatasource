package com.example.multidatasource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.multidatasource.entity.DatabaseConfig;

import java.util.List;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/11/21 11:21 PM
 */
public interface DatabaseConfigService extends IService<DatabaseConfig> {
    List<DatabaseConfig> findList();
}
