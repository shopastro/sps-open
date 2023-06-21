package com.shopastro.sps.open.test;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ye.ly@shopastro-inc.com
 */
@SpringBootTest
public class MapperCodeMaker {
    String template = """
            package com.shopastro.sps.payment.service.dal.mapper;
                        
            import com.shopastro.sps.open.datasource.mybatis.CommonSqlProvider;
            import com.shopastro.sps.open.id.generator.aop.InjectId;
            import com.shopastro.sps.payment.service.dal.entity.%sDO;
            import org.apache.ibatis.annotations.*;
                        
            /**
             * @author ye.ly@shopastro-inc.com
             */
                        
            @Mapper
            public interface %sMapper {
                        
                String TABLE = "%s";
                String COLUMNS = "%s";
                        
                @InjectId
                @SelectProvider(type = CommonSqlProvider.class, method = "insert")
                Integer insert(%sDO row);
                        
                @SelectProvider(type = CommonSqlProvider.class, method = "selectById")
                %sDO selectById(@Param("id") Long id);
                        
                @UpdateProvider(type = CommonSqlProvider.class, method = "updateById")
                Integer updateById(%sDO row);
                        
                        
                @DeleteProvider(type = CommonSqlProvider.class, method = "deleteById")
                Integer deleteById(@Param("id") Long id);
                        
            }
            """;

    @Autowired
    DataSource dataSource;

    String tableName = "billing_record";

    @Test
    public void run() {
        _run(tableName, null);
    }

    @SneakyThrows
    private void _run(String tableName, File dir) {
        JdbcTemplate jt = new JdbcTemplate(dataSource);
        List<String> cols = Lists.newArrayList();
        jt.query("SELECT data_type, COLUMN_NAME FROM information_schema.columns " +
                "WHERE table_schema='sps' AND TABLE_NAME = '" + tableName + "'", (RowMapper) (rs, rowNum) -> {
            String dt = rs.getString(1);
            String cn = rs.getString(2);
            cols.add("`" + cn + "`");
            return null;
        });

        String clzName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
        clzName = StringUtils.upperCase(clzName.charAt(0) + "") + clzName.substring(1);
//        System.out.println(cols);

        String desc = template.formatted(
                clzName, clzName,
                tableName, cols.stream().collect(Collectors.joining(",")),
                clzName, clzName, clzName
        );

        System.out.println(desc);

        if (dir != null) {
            File file = new File(dir, clzName + "Mapper.java");

            Files.write(file.toPath(), desc.getBytes("UTF-8"));
        }
    }

    @Test
    public void write() {
        File dir = new File("/tmp");
        ImmutableList<String> tables = ImmutableList.of(
//                "billing_record",
//                "customer",
                "customer_payment_method",
                "merchant",
                "payment_dispute",
                "payment_order",
                "payment_order_3rd_details",
                "payment_platform_partner",
                "payment_service_provider",
                "payment_transaction",
                "reconciliation",
                "refund_order");

        for (String table : tables) {
            _run(table,dir);
        }

    }
}
