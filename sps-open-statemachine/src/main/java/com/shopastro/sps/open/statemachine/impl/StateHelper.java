package com.shopastro.sps.open.statemachine.impl;

import com.shopastro.sps.open.statemachine.State;

import java.util.Map;

/**
 * StateHelper
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-08 4:23 PM
 */
public class StateHelper {
    public static <S, E, C> State<S, E, C> getState(Map<S, State<S, E, C>> stateMap, S stateId){
        State<S, E, C> state = stateMap.get(stateId);
        if (state == null) {
            state = new StateImpl<>(stateId);
            stateMap.put(stateId, state);
        }
        return state;
    }
}
