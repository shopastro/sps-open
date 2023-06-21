package com.shopastro.sps.open.statemachine.builder;

import com.shopastro.sps.open.statemachine.Condition;

/**
 * On
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-07 6:14 PM
 */
public interface On<S, E, C> extends When<S, E, C>{
    /**
     * Add condition for the transition
     * @param condition transition condition
     * @return When clause builder
     */
    When<S, E, C> when(Condition<C> condition);
}
