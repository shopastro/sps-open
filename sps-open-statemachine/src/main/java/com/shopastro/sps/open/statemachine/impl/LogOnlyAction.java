package com.shopastro.sps.open.statemachine.impl;

import com.shopastro.sps.open.statemachine.Action;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Slf4j
public class LogOnlyAction<S, E, C> implements Action<S, E, C> {

    @Override
    public void execute(S from, S to, E event, C context) {
        if (log.isInfoEnabled()) {
            log.info("action execute: {},{},{},{}", from,to, event, context);
        }
    }
}
