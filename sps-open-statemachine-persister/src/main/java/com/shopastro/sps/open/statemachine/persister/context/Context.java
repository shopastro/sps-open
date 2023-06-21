package com.shopastro.sps.open.statemachine.persister.context;

/**
 * @author ye.ly@shopastro-inc.com
 */
public interface Context {
    Long getTenantId();

    String getBizType();

    String getBizId();

}
