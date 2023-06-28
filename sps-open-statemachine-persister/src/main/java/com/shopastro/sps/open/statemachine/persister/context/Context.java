package com.shopastro.sps.open.statemachine.persister.context;

/**
 * @author ye.ly@shopastro-inc.com
 */
public interface Context<Payload, Output> {
    Long getTenantId();

    String getBizType();

    String getBizId();

    Payload getPayload();

    void setPayload(Payload payload);

    Output getOutput();

    void setOutput(Output output);


}
