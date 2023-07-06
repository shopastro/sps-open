package com.shopastro.sps.open.share.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class ExceptionUtils {

    public static final int STACK_TOP_LINES = 3;

    public static List<String> getRootCauseStackTrace(Throwable throwable, String... prefix) {
        List<String> prefixSet = Optional.ofNullable(prefix).map(it -> Arrays.stream(it).toList()).orElse(Lists.newArrayList());
        String[] exArr = Optional.ofNullable(throwable)
                .map(org.apache.commons.lang3.exception.ExceptionUtils::getRootCauseStackTrace)
                .orElse(ArrayUtils.EMPTY_STRING_ARRAY);
        if (exArr.length < STACK_TOP_LINES) {
            return Arrays.stream(exArr).toList();
        }

        AtomicInteger lines = new AtomicInteger(0);

        return Arrays.stream(exArr).filter(it -> {
                    boolean keep = false;
                    if (lines.get() < STACK_TOP_LINES) {
                        keep = true;
                    } else {
                        for (String s : prefixSet) {
                            if (it.contains(s)) {
                                keep = true;
                                break;
                            }
                        }
                    }
                    lines.incrementAndGet();
                    return keep;
                }).map(String::trim)
                .collect(Collectors.toList());

    }
}
