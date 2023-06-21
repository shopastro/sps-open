package com.shopastro.sps.open.statemachine.builder;

/**
 * @author ye.ly@shopastro-inc.com
 */
public interface BeforeFireEventCallback <S, E, C>{
    void before(S sourceStateId, E event, C ctx);
}
