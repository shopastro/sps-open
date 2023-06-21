package com.shopastro.sps.open.statemachine.builder;

import com.shopastro.sps.open.statemachine.Action;
import com.shopastro.sps.open.statemachine.Condition;
import com.shopastro.sps.open.statemachine.StateMachine;

import java.util.Set;

/**
 * StateMachineBuilder
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 5:32 PM
 */
public interface StateMachineBuilder<S, E, C> {

    StateMachineBuilder<S, E, C> terminalStates(Set<S> terminalStates);

    StateMachineBuilder<S, E, C> initStates(Set<S> initStates);

    StateMachineBuilder<S, E, C> addTransition(S source, S target, E event, Condition<C> condition, Action<S, E, C> action);

    StateMachineBuilder<S, E, C> addAutoTransition(S source, S target, E event, Condition<C> condition, Action<S, E, C> action);

    /**
     * Builder for one transition
     *
     * @return External transition builder
     */
    ExternalTransitionBuilder<S, E, C> externalTransition();

    /**
     * Builder for multiple transitions
     *
     * @return External transition builder
     */
    ExternalTransitionsBuilder<S, E, C> externalTransitions();

    /**
     * Start to build internal transition
     *
     * @return Internal transition builder
     */
    InternalTransitionBuilder<S, E, C> internalTransition();

    /**
     * set up fail callback, default do nothing {@code NumbFailCallbackImpl}
     *
     * @param callback
     */
    void setFailCallback(FailCallback<S, E, C> callback);

    void setBeforeFireEventCallback(BeforeFireEventCallback<S,E,C> beforeFireEventCallback);
    void setAfterFireEventCallback(AfterFireEventCallback<S,E,C> afterFireEventCallback);

    StateMachine<S, E, C> build(String machineId);

    StateMachine<S, E, C> build();

}
