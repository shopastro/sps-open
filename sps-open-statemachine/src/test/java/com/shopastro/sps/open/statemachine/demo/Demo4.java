package com.shopastro.sps.open.statemachine.demo;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.shopastro.sps.open.statemachine.StateMachine;
import com.shopastro.sps.open.statemachine.builder.StateMachineBuilder;
import com.shopastro.sps.open.statemachine.builder.StateMachineBuilderFactory;
import com.shopastro.sps.open.statemachine.impl.LogOnlyAction;
import com.shopastro.sps.open.statemachine.impl.TrueCondition;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class Demo4 {
    @Test
    public void demo4() {
        //配置StateMachine
        StateMachineBuilder<String, String, Map<String, String>> builder = StateMachineBuilderFactory.create();
        builder
                .addTransition("s0", "s1", "e0", new TrueCondition<>(), new LogOnlyAction<>())
                .addTransition("s1", "s2", "e1", new TrueCondition<>(), new LogOnlyAction<>())
                .addTransition("s2", "s2", "e2", new TrueCondition<>(), new LogOnlyAction<>())
                .addTransition("s2", "s3", "e23", new TrueCondition<>(), new LogOnlyAction<>())
                .initStates(ImmutableSet.of("s0")) //初始状态
                .terminalStates(ImmutableSet.of("s3")) //终结状态
        ;
        //创建状态机实例，注意该实例线程安全
        StateMachine<String, String, Map<String, String>> m1 = builder.build();
        // 上下文
        HashMap<String, String> ctx = Maps.newHashMap();
        // 初始状态
        String state = "s0";
        state = m1.fireEvent(state, "e0", ctx);
        assertEquals("s1", state);
        state = m1.fireEvent(state, "e1", ctx);
        assertEquals("s2", state);
        state = m1.fireEvent(state, "e2", ctx); // 自循环流转
        assertEquals("s2", state);
        state = m1.fireEvent(state, "e23", ctx);
        assertEquals("s3", state);

        // 展示uml状态图代码，可以copy到https://www.plantuml.com/plantuml/uml在线查看
        System.out.println(m1.generatePlantUML());
    }
}
