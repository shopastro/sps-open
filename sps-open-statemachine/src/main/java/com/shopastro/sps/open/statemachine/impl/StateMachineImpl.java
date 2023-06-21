package com.shopastro.sps.open.statemachine.impl;

import com.shopastro.sps.open.statemachine.State;
import com.shopastro.sps.open.statemachine.StateMachine;
import com.shopastro.sps.open.statemachine.Transition;
import com.shopastro.sps.open.statemachine.Visitor;
import com.shopastro.sps.open.statemachine.builder.AfterFireEventCallback;
import com.shopastro.sps.open.statemachine.builder.BeforeFireEventCallback;
import com.shopastro.sps.open.statemachine.builder.FailCallback;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * For performance consideration,
 * The state machine is made "stateless" on purpose.
 * Once it's built, it can be shared by multi-thread
 * <p>
 * One side effect is since the state machine is stateless, we can not get current state from State Machine.
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 5:40 PM
 */
public class StateMachineImpl<S, E, C> implements StateMachine<S, E, C> {

    private String machineId;

    private final Map<S, State<S, E, C>> stateMap;

    private Set<S> initStates;
    private Set<S> terminalStates;

    private boolean ready;

    private FailCallback<S, E, C> failCallback;

    private BeforeFireEventCallback<S, E, C> beforeFireEventCallback;
    private AfterFireEventCallback<S, E, C> afterFireEventCallback;

    public StateMachineImpl(Map<S, State<S, E, C>> stateMap) {
        this.stateMap = stateMap;
    }

    @Override
    public boolean verify(S sourceStateId, E event) {
        isReady();

        State sourceState = getState(sourceStateId);

        List<Transition<S, E, C>> transitions = sourceState.getEventTransitions(event);

        return transitions != null && transitions.size() != 0;
    }

    @Override
    public S fireEvent(S sourceStateId, E event, C ctx) {
        State<S,E,C> state = null;
        S targetStateId = null;
        Throwable ex = null;

        try {
            isReady();
            Transition<S, E, C> transition = routeTransition(sourceStateId, event, ctx);

            if (transition == null) {
                Debugger.debug("There is no Transition for " + event);
                failCallback.onFail(sourceStateId, event, ctx);
                targetStateId = sourceStateId;
            }
            beforeFireEventCallback.before(sourceStateId, event, ctx);
            state = transition.transit(ctx, false);
        } catch (Throwable e) {
            ex = e;
            throw e;
        } finally {
            afterFireEventCallback.after(sourceStateId, targetStateId, event, ctx, ex);
        }

        return state == null ? null : autoTrigger(state, ctx);
    }

    private S autoTrigger(State<S, E, C> state, C ctx) {
        S stateId = state.getId();

        List<E> autoEvents = state.getAllTransitions().stream().filter(Transition::isAuto)
                .map(Transition::getEvent)
                .collect(Collectors.toList());

        for (int i = 0; i < autoEvents.size(); i++) {
            E event = autoEvents.get(i);
            Debugger.debug("auto trigger events,{},{}", state.getId(), event);
            S afterAutoFireStateId = fireEvent(stateId, event, ctx);
            // state already changed,not need fire remain events
            if (!afterAutoFireStateId.equals(stateId)) {
                Debugger.debug("break auto trigger,skip remain events,{},{}", state.getId(),
                        autoEvents.subList(i + 1, autoEvents.size()));
                stateId = afterAutoFireStateId;
                break;
            }

        }
        return stateId;
    }


    private Transition<S, E, C> routeTransition(S sourceStateId, E event, C ctx) {
        State sourceState = getState(sourceStateId);

        List<Transition<S, E, C>> transitions = sourceState.getEventTransitions(event);

        if (transitions == null || transitions.size() == 0) {
            return null;
        }

        Transition<S, E, C> transit = null;
        for (Transition<S, E, C> transition : transitions) {
            if (transition.getCondition() == null) {
                transit = transition;
            } else if (transition.getCondition().isSatisfied(ctx)) {
                transit = transition;
                break;
            }
        }

        return transit;
    }

    private State getState(S currentStateId) {
        State state = StateHelper.getState(stateMap, currentStateId);
        if (state == null) {
            showStateMachine();
            throw new StateMachineException(currentStateId + " is not found, please check state machine");
        }
        return state;
    }

    private void isReady() {
        if (!ready) {
            throw new StateMachineException("State machine is not built yet, can not work");
        }
    }

    @Override
    public String accept(Visitor visitor) {
        StringBuilder sb = new StringBuilder();
        sb.append(visitor.visitOnEntry(this));
        for (State state : stateMap.values()) {
            sb.append(state.accept(visitor));
        }
        sb.append(visitor.visitOnExit(this));
        return sb.toString();
    }

    @Override
    public void showStateMachine() {
        SysOutVisitor sysOutVisitor = new SysOutVisitor();
        accept(sysOutVisitor);
    }

    @Override
    public String generatePlantUML() {
        PlantUMLVisitor plantUMLVisitor = new PlantUMLVisitor();
        return accept(plantUMLVisitor);
    }

    @Override
    public void setInitStates(Set<S> initStates) {
        this.initStates = initStates;
    }

    @Override
    public void setTerminalStates(Set<S> terminalStates) {
        this.terminalStates = terminalStates;
    }

    @Override
    public Set<S> getInitStates() {
        return initStates;
    }

    @Override
    public Set<S> getTerminalStates() {
        return terminalStates;
    }


    @Override
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setFailCallback(FailCallback<S, E, C> failCallback) {
        this.failCallback = failCallback;
    }

    public void setBeforeFireEventCallback(BeforeFireEventCallback<S, E, C> beforeFireEventCallback) {
        this.beforeFireEventCallback = beforeFireEventCallback;
    }

    public void setAfterFireEventCallback(AfterFireEventCallback<S, E, C> afterFireEventCallback) {
        this.afterFireEventCallback = afterFireEventCallback;
    }
}
