package com.shopastro.sps.open.datasource.mybatis;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class JsonTypeConfiguration implements ConfigurationCustomizer {

    final JsonTypeRegister jsonTypeRegister;

    public JsonTypeConfiguration(JsonTypeRegister jsonTypeRegister) {
        this.jsonTypeRegister = jsonTypeRegister;
    }

    @Override
    public void customize(Configuration configuration) {
        for (Class type : jsonTypeRegister.types) {
            configuration.getTypeHandlerRegistry().register(type, null, new JsonTypeHandler<>(type));
        }

    }
}