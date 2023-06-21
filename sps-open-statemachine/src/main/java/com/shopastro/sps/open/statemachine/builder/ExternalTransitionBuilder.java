package com.shopastro.sps.open.statemachine.builder;

/**
 * ExternalTransitionBuilder
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 6:11 PM
 */
public interface ExternalTransitionBuilder<S, E, C> {
    /**
     * Build transition source state.
     * @param stateId id of state
     * @return from clause builder
     */
    From<S, E, C> from(S stateId);

}
