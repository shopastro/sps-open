package com.shopastro.sps.open.share.rpc;

import com.shopastro.sps.open.share.page.PageQueryResult;
import com.shopastro.sps.open.share.page.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author ye.ly@shopastro-inc.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RpcResult<T> implements Serializable {
    public static final String HEADER_TRACE_ID = "trace-id";
    public static final String HEADER_EXCEPTION_STACK = "exception-stack";

    /**
     * 扩展字段
     */
    @Builder.Default
    private HashMap<String, String> header = new HashMap<>();

    private T data;
    private boolean success = true;

    private String errCode;

    private String errMsg;

    private Pagination page;

    public void putHeader(String key, String value) {
        header.put(StringUtils.trimToEmpty(key), StringUtils.trimToEmpty(value));
    }

    public T tryGetSuccessfulData() throws RpcResultException {
        if (!success) {
            throw new RpcResultException(this);
        } else {
            return this.getData();
        }
    }

    public static <T> RpcResult<T> success(T data) {
        RpcResult<T> rpcResult = RpcResult.<T>builder().data(data).success(true).build();
        if (data instanceof PageQueryResult) {
            PageQueryResult pageQueryResult = (PageQueryResult) data;
            Pagination pagination = pageQueryResult.getPage();
            rpcResult.setPage(pagination);
        }
        return rpcResult;
    }

}

