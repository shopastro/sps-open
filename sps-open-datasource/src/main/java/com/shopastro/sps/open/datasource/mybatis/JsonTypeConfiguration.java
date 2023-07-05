package com.shopastro.sps.open.datasource.mybatis;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

import java.util.Map;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class JsonTypeConfiguration implements ConfigurationCustomizer {

    final Map<String,JsonTypeRegister> registers;

    public JsonTypeConfiguration(Map<String, JsonTypeRegister> registers) {
        this.registers = registers;
    }


    @Override
    public void customize(Configuration configuration) {

        for (JsonTypeRegister register : registers.values()) {
            for (Class type : register.getRegisterTypes()) {
                configuration.getTypeHandlerRegistry().register(type, null, new JsonTypeHandler<>(type));
            }
        }

        configuration.getTypeHandlerRegistry().register(Boolean.class, null, new BooleanTypeHandler());
        configuration.getTypeHandlerRegistry().register(boolean.class, null, new BooleanTypeHandler());
    }
}