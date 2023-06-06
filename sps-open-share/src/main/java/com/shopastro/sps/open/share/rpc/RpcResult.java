package com.shopastro.sps.open.share.rpc;

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

    private String i18nErrCode;

    private String keyOperation;

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

    /**
     * 通用成功请求
     */
    public static <T> RpcResult<T> success(T data) {
        return RpcResult.<T>builder().data(data).success(true).build();
    }

    /**
     * 分页-成功请求
     */
    public static <T> RpcResult<T> success(T data, Integer pageIndex, Integer pageSize, Long total) {
        Pagination page = Pagination.builder().pageIndex(pageIndex).pageSize(pageSize).total(total).build();
        return RpcResult.<T>builder().success(true).data(data).page(page).build();
    }

    /**
     * 分页-成功请求
     */
    public static <T> RpcResult<T> success(T data, Pagination pagination) {
        Pagination page = Pagination.builder().pageIndex(pagination.getPageIndex()).pageSize(pagination.getPageSize()).total(pagination.getTotal()).build();
        return RpcResult.<T>builder().success(true).data(data).page(page).build();
    }

    /**
     * 通用失败请求
     */
    @Deprecated
    public static <T> RpcResult<T> error(String errCode, String errMsg) {
        return RpcResult.<T>builder().success(false).errCode(errCode).errMsg(errMsg).build();
    }

    /**
     * 通用失败请求
     */
    public static <T> RpcResult<T> i18nError(SysErrorMsgEnum sysErrorMsgEnum) {
        return RpcResult.<T>builder()
                .success(false)
                .i18nErrCode(sysErrorMsgEnum.getI18nErrCode())
                .errCode(sysErrorMsgEnum.getErrCode())
                .errMsg(sysErrorMsgEnum.getErrMsg()).build();
    }

    /**
     * 通用失败请求
     */
    public static <T> RpcResult<T> i18nError(String i18nErrCode, String errCode, String errMsg) {
        return RpcResult.<T>builder()
                .success(false)
                .i18nErrCode(i18nErrCode)
                .errCode(errCode)
                .errMsg(errMsg).build();
    }

    /**
     * 通用失败请求
     */
    public static <T> RpcResult<T> i18nError(String i18nErrCode, String errCode, String errMsg, String keyOperation) {
        return RpcResult.<T>builder()
                .success(false)
                .i18nErrCode(i18nErrCode)
                .errCode(errCode)
                .errMsg(errMsg)
                .keyOperation(keyOperation).build();
    }


    public static <T> RpcResult<T> i18nError(String i18nErrCode) {
        return i18nError(i18nErrCode, i18nErrCode, i18nErrCode);
    }
}

