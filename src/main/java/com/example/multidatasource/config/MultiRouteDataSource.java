package com.example.multidatasource.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Description: 功能描述
 * @Author: shiwei
 * @Date: 8/11/21 11:49 PM
 */
public class MultiRouteDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        // 通过绑定线程的数据源上下文实现多数据源的动态切换,有兴趣的可以去查阅资料或源码
        String datasource = DataSourceContext.getDataSource();
        if (StringUtils.isBlank(datasource)) {
            datasource = "master";
        }
        logger.info("datasource=" + datasource);
        return datasource;
    }
}
