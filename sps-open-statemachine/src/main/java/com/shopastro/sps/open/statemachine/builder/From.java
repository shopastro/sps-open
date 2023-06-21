package com.shopastro.sps.open.statemachine.builder;

/**
 * From
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 6:13 PM
 */
public interface From<S, E, C> {
    /**
     * Build transition target state and return to clause builder
     * @param stateId id of state
     * @return To clause builder
     */
    To<S, E, C> to(S stateId);

}
