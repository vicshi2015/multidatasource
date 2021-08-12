package com.example.multidatasource.config;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/11/21 11:49 PM
 */
public class DataSourceContext {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String value) {
        contextHolder.set(value);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
