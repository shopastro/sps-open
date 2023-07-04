package com.shopastro.sps.open.statemachine.persister.context;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ye.ly@shopastro-inc.com
 */

public interface Context<Payload, Output> {

    Logger logger = LoggerFactory.getLogger(Context.class);

    default Long getTenantId() {
        if (getPayload() != null) {
            try {
                Object shopId = PropertyUtils
                        .getProperty(getPayload(), "shopId");
                return shopId == null ? null : Long.parseLong(shopId.toString());
            } catch (Exception e) {
                logger.warn("get shopId fail,{}", e.toString());
            }
        }
        return null;
    }

    default String getBizType() {
        if (getPayload() == null) {
            return null;
        } else {
            return getPayload().getClass().getSimpleName();
        }
    }

    default String getBizId() {
        if (getPayload() != null) {
            try {
                Object id = PropertyUtils
                        .getProperty(getPayload(), "id");
                return id == null ? null : id.toString();
            } catch (Exception e) {
                logger.warn("get shopId fail,{}", e.toString());
            }
        }
        return null;
    }

    Payload getPayload();

    void setPayload(Payload payload);

    Output getOutput();

    void setOutput(Output output);


}
