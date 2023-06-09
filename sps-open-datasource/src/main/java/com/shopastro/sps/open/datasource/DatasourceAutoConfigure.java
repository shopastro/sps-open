package com.shopastro.sps.open.datasource;

import com.shopastro.sps.open.datasource.mybatis.JsonTypeConfiguration;
import com.shopastro.sps.open.datasource.mybatis.JsonTypeRegister;
import com.shopastro.sps.open.datasource.mybatis.PageQueryAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * UID 的自动配置
 *
 * @author wujun
 * @date 2019.02.20 10:57
 */
@Configuration
public class DatasourceAutoConfigure {
    @Bean
    @ConditionalOnMissingBean
    public PageQueryAspect pageQueryAspect() {
        return new PageQueryAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public JsonTypeConfiguration jsonTypeConfiguration(Map<String,JsonTypeRegister> jsonTypeRegisters) {
        return new JsonTypeConfiguration(jsonTypeRegisters);
    }
}
