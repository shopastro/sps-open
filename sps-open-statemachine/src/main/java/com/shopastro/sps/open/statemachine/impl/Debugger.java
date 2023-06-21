package com.shopastro.sps.open.statemachine.impl;

import lombok.extern.slf4j.Slf4j;

/**
 * Debugger, This is used to decouple Logging framework dependency
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-11 11:08 AM
 */
@Slf4j
public class Debugger {


    public static void debug(String message) {
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }

    public static void debug(String message, Object... args) {
        if (log.isInfoEnabled()) {
            log.info(message, args);
        }
    }

}
