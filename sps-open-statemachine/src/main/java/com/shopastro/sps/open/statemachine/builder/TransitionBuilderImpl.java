package com.shopastro.sps.open.statemachine.builder;

import com.shopastro.sps.open.statemachine.Action;
import com.shopastro.sps.open.statemachine.Condition;
import com.shopastro.sps.open.statemachine.State;
import com.shopastro.sps.open.statemachine.Transition;
import com.shopastro.sps.open.statemachine.impl.StateHelper;
import com.shopastro.sps.open.statemachine.impl.TransitionType;

import java.util.Map;

/**
 * TransitionBuilderImpl
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 10:20 PM
 */
class TransitionBuilderImpl<S,E,C> implements ExternalTransitionBuilder<S,E,C>, InternalTransitionBuilder<S,E,C>, From<S,E,C>, On<S,E,C>, To<S,E,C>,Auto {

    final Map<S, State<S, E, C>> stateMap;

    private State<S, E, C> source;

    protected State<S, E, C> target;

    private Transition<S, E, C> transition;

    final TransitionType transitionType;

    public TransitionBuilderImpl(Map<S, State<S, E, C>> stateMap, TransitionType transitionType) {
        this.stateMap = stateMap;
        this.transitionType = transitionType;
    }

    @Override
    public From<S, E, C> from(S stateId) {
        source = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public To<S, E, C> to(S stateId) {
        target = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public To<S, E, C> within(S stateId) {
        source = target = StateHelper.getState(stateMap, stateId);
        return this;
    }
    @Override
    public When<S, E, C> when(Condition<C> condition) {
        transition.setCondition(condition);
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        transition = source.addTransition(event, target, transitionType);
        return this;
    }

    @Override
    public Auto perform(Action<S, E, C> action) {
        transition.setAction(action);
        return this;
    }


    @Override
    public void auto(boolean auto) {
        transition.setAuto(auto);
    }
}
