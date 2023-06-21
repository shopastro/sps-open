package com.shopastro.sps.open.statemachine.builder;

import com.shopastro.sps.open.statemachine.*;
import com.shopastro.sps.open.statemachine.impl.StateMachineImpl;
import com.shopastro.sps.open.statemachine.impl.TransitionType;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StateMachineBuilderImpl
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 9:40 PM
 */
public class StateMachineBuilderImpl<S, E, C> implements StateMachineBuilder<S, E, C> {

    /**
     * StateMap is the same with stateMachine, as the core of state machine is holding reference to states.
     */
    private final Map<S, State<S, E, C>> stateMap = new ConcurrentHashMap<>();
    private final StateMachineImpl<S, E, C> stateMachine = new StateMachineImpl<>(stateMap);
    private FailCallback<S, E, C> failCallback = new NumbFailCallback<>();

    private BeforeFireEventCallback<S, E, C> beforeFireEventCallback = (sourceStateId, event, ctx) -> {
    };
    private AfterFireEventCallback<S, E, C> afterFireEventCallback = (sourceStateId, targetStateId, event, ctx, throwable) -> {
    };

    private Set<E> autoEvents;

    private Set<S> initStates;
    private Set<S> terminalStates;


    @Override
    public StateMachineBuilder<S, E, C> terminalStates(Set<S> terminalStates) {
        this.terminalStates = terminalStates;
        return this;
    }

    @Override
    public StateMachineBuilder<S, E, C> initStates(Set<S> initStates) {
        this.initStates = initStates;
        return this;
    }

    @Override
    public StateMachineBuilder<S, E, C> addTransition(S source, S target, E event, Condition<C> condition, Action<S, E, C> action) {
        return addTransition(source, target, event, condition, action, false);
    }

    public StateMachineBuilder<S, E, C> addAutoTransition(S source, S target, E event, Condition<C> condition, Action<S, E, C> action) {
        return addTransition(source, target, event, condition, action, true);
    }

    private StateMachineBuilder<S, E, C> addTransition(S source, S target, E event, Condition<C> condition, Action<S, E, C> action, boolean auto) {
        if (Objects.equals(source, target)) {
            internalTransition().within(source).on(event).when(condition).perform(action).auto(auto);
        } else {
            externalTransition().from(source).to(target).on(event).when(condition).perform(action).auto(auto);
        }
        return this;
    }

    @Override
    public ExternalTransitionBuilder<S, E, C> externalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public ExternalTransitionsBuilder<S, E, C> externalTransitions() {
        return new TransitionsBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public InternalTransitionBuilder<S, E, C> internalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.INTERNAL);
    }

    @Override
    public void setFailCallback(FailCallback<S, E, C> callback) {
        this.failCallback = callback;
    }

    @Override
    public void setBeforeFireEventCallback(BeforeFireEventCallback<S, E, C> beforeFireEventCallback) {
        this.beforeFireEventCallback = beforeFireEventCallback;
    }

    @Override
    public void setAfterFireEventCallback(AfterFireEventCallback<S, E, C> afterFireEventCallback) {
        this.afterFireEventCallback = afterFireEventCallback;
    }

    @Override
    public StateMachine<S, E, C> build(String machineId) {
        stateMachine.setMachineId(machineId);
        stateMachine.setReady(true);
        stateMachine.setFailCallback(failCallback);
        stateMachine.setInitStates(initStates);
        stateMachine.setTerminalStates(terminalStates);
        stateMachine.setBeforeFireEventCallback(beforeFireEventCallback);
        stateMachine.setAfterFireEventCallback(afterFireEventCallback);
        StateMachineFactory.register(stateMachine);
        return stateMachine;
    }

    @Override
    public StateMachine<S, E, C> build() {
        return build(UUID.randomUUID().toString());
    }

}
