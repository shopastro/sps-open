package com.shopastro.sps.open.statemachine.builder;

/**
 * @author ye.ly@shopastro-inc.com
 */
public interface AfterFireEventCallback<S, E, C>{
    void after(S sourceStateId, S targetStateId, E event,C beforeContext, C AfterContext , Throwable throwable);
}
