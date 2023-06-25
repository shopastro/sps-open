package com.shopastro.sps.open.statemachine.persister.dal.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ye.ly@shopastro-inc.com
 */
@SpringBootTest
class StateMachineTraceMapperTest {
    @Autowired
    StateMachineTraceMapper mapper;

    @Test
    public void test() {
        System.out.println(mapper);
    }
}