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
class StateMachineTest {
    @Test
    public void testAutoEvent() {
        StateMachineBuilder<String, String, Map<String, String>> builder = StateMachineBuilderFactory.create();
        builder.setBeforeFireEventCallback((sourceStateId, event, ctx) -> {
            System.out.println("before: " + ctx);
            ctx.put("kk", "vv");
        });
        builder.setAfterFireEventCallback((sourceStateId, targetStateId, event, beforeCtx, ctx, throwable) -> System.out.println("after: " + ctx + "," + sourceStateId + "," + targetStateId));
        StateMachine<String, String, Map<String, String>> m1 = builder
                .addTransition("s0", "s1", "e01", new TrueCondition<>(), new LogOnlyAction<>())
                .addTransition("s0", "s2", "e02", new TrueCondition<>(), new LogOnlyAction<>())
                .addAutoTransition("s1", "s3", "e13", new TrueCondition<>(), new LogOnlyAction<>())
                .build();
        HashMap<String, String> ctx = Maps.newHashMap();
        ctx.put("k", "v");

        Object state = m1.fireEvent("s0", "e01", ctx);
        System.out.println(state);

        assertEquals("s3", state);
    }

    @Test
    public void testAutoEvent2() {
        StateMachineBuilder<String, String, Map<String, String>> builder = StateMachineBuilderFactory.create();
        StateMachine<String, String, Map<String, String>> m1 = builder
                .addTransition("s0", "s1", "e01", new TrueCondition<>(), new LogOnlyAction<>())
                .addTransition("s0", "s2", "e02", new TrueCondition<>(), new LogOnlyAction<>())
                .addAutoTransition("s1", "s3", "e13", new Condition<Map<String, String>>() {
                    boolean first = true;

                    @Override
                    public boolean isSatisfied(Map<String, String> context) {
                        if (first) {
                            log.info("block by first");
                            first = false;
                            return false;
                        }
                        log.info("allow");
                        return true;

                    }
                }, new LogOnlyAction<>())
                .build();

        HashMap<String, String> ctx = Maps.newHashMap();

        Object state = null;
        try {
            state = m1.fireEvent("s0", "e01", ctx);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        System.out.println(state);

        // auto fire is blocked by condition
        assertEquals("s1", state);
        // second fire e13 by manual

        state = m1.fireEvent("s1", "e13", ctx);
        assertEquals("s3", state);
    }


    @Test
    public void testAutoEvent3() {
        StateMachineBuilder<String, String, Map<String, String>> builder = StateMachineBuilderFactory.create();
        StateMachine<String, String, Map<String, String>> m1 = builder
                .addTransition("s0", "s1", "e01", new TrueCondition<>(), new LogOnlyAction<>())
                .addTransition("s0", "s2", "e02", new TrueCondition<>(), new LogOnlyAction<>())
                .addAutoTransition("s1", "s3", "e13", new TrueCondition<>(), new LogOnlyAction<>())
                .addAutoTransition("s1", "s4", "e14", new TrueCondition<>(), new LogOnlyAction<>())
                .build();

        HashMap<String, String> ctx = Maps.newHashMap();

        Object state = m1.fireEvent("s0", "e01", ctx);
        System.out.println(state);

        assertEquals("s3", state);
    }

}