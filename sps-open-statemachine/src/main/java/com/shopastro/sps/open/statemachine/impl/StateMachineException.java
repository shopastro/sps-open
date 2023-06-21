package com.shopastro.sps.open.statemachine.impl;

/**
 * StateMachineException
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-08 5:28 PM
 */
public class StateMachineException extends RuntimeException{
    public StateMachineException(String message){
        super(message);
    }
}
