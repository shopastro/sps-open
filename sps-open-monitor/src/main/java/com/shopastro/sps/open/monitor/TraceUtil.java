package com.shopastro.sps.open.monitor;

import io.opentelemetry.api.trace.Span;

/**
 * @author ye.ly@shopastro-inc.com
 */
public class TraceUtil {
    public static String getTraceId(){
        return Span.current().getSpanContext().getTraceId();
    }
}
