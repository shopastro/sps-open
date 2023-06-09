package com.shopastro.sps.open.statemachine.builder;

/**
 * To
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 6:14 PM
 */
public interface To<S, E, C> {
    /**
     * Build transition event
     * @param event transition event
     * @return On clause builder
     */
    On<S, E, C> on(E event);
}
