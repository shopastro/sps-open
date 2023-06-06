package com.shopastro.sps.open.sample.center;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ye.ly@shopastro-inc.com
 */
@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "com.shopastro.sps.open.sample.center.dao")
public class CenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class, args);
    }
}
