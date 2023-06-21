package com.shopastro.sps.open.statemachine.builder;

/**
 * StateMachineBuilderFactory
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-08 12:33 PM
 */
public class StateMachineBuilderFactory {
    public static <S, E, C> StateMachineBuilder<S, E, C> create(){
        return new StateMachineBuilderImpl<>();
    }
}
