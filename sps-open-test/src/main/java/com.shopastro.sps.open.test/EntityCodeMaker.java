package com.shopastro.sps.open.test;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author ye.ly@shopastro-inc.com
 */
@SpringBootTest
public class EntityCodeMaker {
    @Autowired
    DataSource dataSource;

    String clzSuffix = "DO";

    Map<String, String> typeMapping = ImmutableMap.of(
            "bigint", "Long",
            "char", "String",
            "varchar", "String",
            "timestamp", "Date",
            "datetime", "Date"

    );

    List<String> skipColumns = ImmutableList.of("id", "gmtCreate", "gmtModified", "isDeleted", "jsonData");

    String ddlTemplate = """
            import com.shopastro.sps.open.share.BaseDataObjectWithJsonColumn;
            import lombok.AllArgsConstructor;
            import lombok.Data;
            import lombok.EqualsAndHashCode;
            import lombok.NoArgsConstructor;
            import lombok.experimental.SuperBuilder;
                        
            /**
             * @author ye.ly@shopastro-inc.com
             */
            @EqualsAndHashCode(callSuper = true)
            @Data
            @SuperBuilder
            @AllArgsConstructor
            @NoArgsConstructor
            public class %s extends BaseDataObjectWithJsonColumn {
            %s                
            }
            """;

    @Test
    public void run() {

        String tableName = "payment_transaction";
        System.out.println(dataSource);
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        StringBuilder desc = new StringBuilder();
        jt.query("SELECT data_type, COLUMN_NAME FROM information_schema.columns " +
                "WHERE table_schema='sps' AND TABLE_NAME = '" + tableName + "'", new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                String dt = rs.getString(1);
                dt = typeMapping.get(dt);
                String cn = rs.getString(2);
                cn = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, cn);
                if (!skipColumns.contains(cn)) {
                    System.out.println(dt + " " + cn + ";");
                    desc.append("    " + dt + " " + cn + ";\n");
                }
                return null;
            }
        });

        String clzName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
        clzName = StringUtils.upperCase(clzName.charAt(0) + "") + clzName.substring(1);
        clzName = clzName + clzSuffix;
        System.out.println("Class Name: " + clzName);
        System.out.println("\n\n");
        String ddl = ddlTemplate.formatted(
                clzName, desc
        );
        System.out.println(ddl);
    }


}
