package com.shopastro.sps.open.datasource;

import com.shopastro.sps.open.datasource.mybatis.JsonTypeConfiguration;
import com.shopastro.sps.open.datasource.mybatis.JsonTypeRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * UID 的自动配置
 *
 * @author wujun
 * @date 2019.02.20 10:57
 */
@Configuration
public class DatasourceAutoConfigure {

    @Bean
    public JsonTypeRegister jsonTypeRegister() {
        return new JsonTypeRegister();
    }

    @Bean
    public JsonTypeConfiguration jsonTypeConfiguration(JsonTypeRegister jsonTypeRegister) {
        return new JsonTypeConfiguration(jsonTypeRegister);
    }
}
