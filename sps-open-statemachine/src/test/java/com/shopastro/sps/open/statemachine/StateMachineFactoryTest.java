package com.shopastro.sps.open.statemachine;

import com.google.common.collect.Maps;
import com.shopastro.sps.open.statemachine.builder.StateMachineBuilder;
import com.shopastro.sps.open.statemachine.builder.StateMachineBuilderFactory;
import com.shopastro.sps.open.statemachine.impl.LogOnlyAction;
import com.shopastro.sps.open.statemachine.impl.TrueCondition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Slf4j
class StateMachineFactoryTest {
    @Test
    public void testBuilder() {
        StateMachineBuilder<String, String, Map<String, String>> builder = StateMachineBuilderFactory.create();
        builder.setFailCallback((sourceState, event, context) -> {
            assertEquals("s0", sourceState);
            assertEquals("e0", event);
            log.info("transfer error,{},{}", sourceState, event);
        });
        StateMachine<String, String, Map<String, String>> m1 = builder.build();
        HashMap<String, String> ctx = Maps.newHashMap();
        String state = m1.fireEvent("s0", "e0", ctx);
        System.out.println(state);

        assertEquals("s0", state);
    }

    @Test
    public void testBuilderSlim() {
        StateMachineBuilder<String, String, Map<String, String>> builder = StateMachineBuilderFactory.create();
        StateMachine<String, String, Map<String, String>> m1 = builder
                .addTransition("s0", "s1", "e01", new TrueCondition<>(), new LogOnlyAction<>())
                .addTransition("s0", "s2", "e02", new TrueCondition<>(), new LogOnlyAction<>())
                .build();

        HashMap<String, String> ctx = Maps.newHashMap();

        Object state = m1.fireEvent("s0", "e01", ctx);
        System.out.println(state);

        assertEquals("s1", state);
    }

}