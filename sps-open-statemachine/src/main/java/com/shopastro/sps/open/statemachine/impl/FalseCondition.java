package com.shopastro.sps.open.statemachine.impl;

import com.shopastro.sps.open.statemachine.Condition;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Slf4j
public class FalseCondition<C> implements Condition<C> {
    @Override
    public boolean isSatisfied(C context) {
        if (log.isInfoEnabled()) {
            log.info("condition context: {}", context);
        }
        return false;
    }
}
