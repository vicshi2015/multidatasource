package com.example.multidatasource;

import com.example.multidatasource.utils.DataSourceUtil;
import com.example.multidatasource.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.example.multidatasource.dao")
@Slf4j
public class MultidatasourceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(MultidatasourceApplication.class, args);

        log.info("springboot启动成功");
        SpringContextUtil.setApplicationContext(applicationContext);
        DataSourceUtil.initDataSource();
    }

}
