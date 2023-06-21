package com.shopastro.sps.open.statemachine;

/**
 * Visitable
 *
 * @author ye.ly@shopastro-inc.com
 * @date 2020-02-08 8:41 PM
 */
public interface Visitable {
    String accept(final Visitor visitor);
}
