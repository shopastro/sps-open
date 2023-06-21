package com.shopastro.sps.open.statemachine.builder;

import com.shopastro.sps.open.statemachine.exception.TransitionFailException;

/**
 * Alert fail callback, throw an {@code TransitionFailException}
 *
 * 
 * @date 2022/9/15 12:02 PM
 */
public class AlertFailCallback<S, E, C> implements FailCallback<S, E, C> {

    @Override
    public void onFail(S sourceState, E event, C context) {
        throw new TransitionFailException(
            "Cannot fire event [" + event + "] on current state [" + sourceState + "] with context [" + context + "]"
        );
    }
}
