package com.example.multidatasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/11/21 11:19 PM
 */
@Data
public class DatabaseConfig {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String name;
    private String url;
    private String userName;
    private String password;
}
