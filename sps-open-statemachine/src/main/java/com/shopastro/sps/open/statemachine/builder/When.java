package com.shopastro.sps.open.statemachine.builder;

import com.shopastro.sps.open.statemachine.Action;

/**
 * When
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 9:33 PM
 */
public interface When<S, E, C>{
    /**
     * Define action to be performed during transition
     *
     * @param action performed action
     */
    Auto perform(Action<S, E, C> action);
}
