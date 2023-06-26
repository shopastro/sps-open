package com.shopastro.sps.open.datasource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ye.ly@shopastro-inc.com
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DatasourceTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void test() {
        System.out.println(dataSource);
        assertNotNull(dataSource);
    }
}
