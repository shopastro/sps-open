package test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

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
