package com.shopastro.sps.open.datasource.mybatis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.*;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }


    private final Class<T> javaType;

    public JsonTypeHandler(Class<T> javaType) {
        this.javaType = javaType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setObject(i, mapper.writeValueAsString(parameter), Types.VARCHAR);
        } catch (JsonProcessingException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {

        return toJavaTypeObject(rs.getObject(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        return toJavaTypeObject(rs.getObject(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        return toJavaTypeObject(cs.getObject(columnIndex));
    }

    private T toJavaTypeObject(Object value) throws SQLException {

        if (value == null) {
            return null;
        }

        try {
            return mapper.readValue(value.toString(), javaType);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }
}
