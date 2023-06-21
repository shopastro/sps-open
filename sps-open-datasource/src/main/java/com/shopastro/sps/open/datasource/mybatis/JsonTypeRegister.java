package com.shopastro.sps.open.datasource.mybatis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class JsonTypeRegister {
    Set<Class> types = new HashSet<>();

    public void register(Class type) {
        types.add(type);
    }

    public void register(Class... types) {
        this.types.addAll(Arrays.asList(types));
    }
}
