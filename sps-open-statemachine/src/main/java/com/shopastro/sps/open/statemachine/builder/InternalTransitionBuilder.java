package com.shopastro.sps.open.statemachine.builder;

/**
 * InternalTransitionBuilder
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 9:39 PM
 */
public interface InternalTransitionBuilder <S, E, C> {
    /**
     * Build a internal transition
     * @param stateId id of transition
     * @return To clause builder
     */
    To<S, E, C> within(S stateId);
}
