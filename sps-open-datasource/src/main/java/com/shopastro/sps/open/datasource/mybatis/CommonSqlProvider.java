package com.shopastro.sps.open.datasource.mybatis;


import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableSet;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class CommonSqlProvider {
    public String insert(ProviderContext providerContext) {
        return exec(providerContext, (table, columns) -> {
            String properties = toCloumnStream(columns).map(it -> {
                        if ("gmt_create".equals(it) || "gmt_modified".equals(it)) {
                            return "now()";
                        } else if ("is_deleted".equals(it)) {
                            return "'N'";
                        } else {
                            return
                                    "#{" +
                                            toCamel(it)
                                            + "}"
                                    ;
                        }
                    }

            ).collect(Collectors.joining(","));
            return """
                    insert into %s (%s) values (%s)""".formatted(table, columns, properties);
        });
    }


    public String updateById(ProviderContext providerContext) {
        return exec(providerContext, (table, columns) ->
                """
                        update %s set gmt_modified=now(),%s where is_deleted='N' and id=#{id} and shop_id=#{shopId}""".formatted(table, makeUpdateSnipe(columns))
        );
    }

    public String deleteById(ProviderContext providerContext) {
        return exec(providerContext, (table, columns) ->
                """
                        update %s set gmt_modified=now(),is_deleted='Y' where is_deleted='N' and id=#{id} and shop_id=#{shopId}""".formatted(table)
        );
    }

    public String selectById(ProviderContext providerContext) {
        return exec(providerContext, (table, columns) ->
                """
                        select %s from %s where is_deleted='N' and id=#{id} and shop_id=#{shopId}""".formatted(columns, table));
    }

    public String selectAll(ProviderContext providerContext) {
        return exec(providerContext, (table, columns) ->
                """
                        select %s from %s where is_deleted='N' and shop_id=#{shopId} order by ${orderBy}""".formatted(columns, table));
    }

    @SneakyThrows
    private String getStringValue(Class clz, String name) {
        Field f = FieldUtils.getDeclaredField(clz, name);
        return (String) f.get(clz);
    }

    private String makeUpdateSnipe(String columns) {
        Set<String> skips = ImmutableSet.of("id", "gmt_create", "gmt_modified", "is_deleted");
        return toCloumnStream(columns)
                .filter(it -> !skips.contains(it))
                .map(it ->
                        "%s=#{%s}".formatted(it, toCamel(it))
                ).collect(Collectors.joining(","));
    }

    private Stream<String> toCloumnStream(String columns) {
        return Arrays.stream(columns.split(",")).map(it -> {
                    it = it.trim();
                    it = StringUtils.remove(it.trim(), "`");
                    return it;
                }
        );
    }

    private String exec(ProviderContext providerContext, BiFunction<String, String, String> fn) {
        Class<?> mapperType = providerContext.getMapperType();
        String table = getStringValue(mapperType, "TABLE");
        String columns = getStringValue(mapperType, "COLUMNS");
        return fn.apply(table, columns);
    }

    private static String toCamel(String it) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, it);
    }
}
