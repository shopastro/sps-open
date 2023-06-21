package com.shopastro.sps.open.share.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class ExceptionUtils {

    public static List<String> getRootCauseStackTrace(Throwable throwable, String... prefix) {
        List<String> prefixSet = Optional.ofNullable(prefix).map(it -> Arrays.stream(it).toList()).orElse(Lists.newArrayList());
        String[] exArr = Optional.ofNullable(throwable)
                .map(org.apache.commons.lang3.exception.ExceptionUtils::getRootCauseStackTrace)
                .orElse(ArrayUtils.EMPTY_STRING_ARRAY);
        return Arrays.stream(exArr).filter(it -> {
                    for (String s : prefixSet) {
                        if (it.contains(s)) {
                            return true;
                        }
                    }
                    return false;
                }).map(String::trim)
                .collect(Collectors.toList());

    }
}
