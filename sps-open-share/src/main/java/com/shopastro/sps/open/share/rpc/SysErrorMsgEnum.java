package com.shopastro.sps.open.share.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysErrorMsgEnum {


    system_error("APPT500000", "系统修复中，请稍后再试。错误码500"),
    sys_busy("APPT100000", "系统修复中，请稍后再试。错误码1000"),
    rpc_call_error("APPT100014", "系统修复中，请稍后再试。错误码2006"),

    ;

    private final String errCode;
    private final String errMsg;

    public String errorCode() {
        return errCode;
    }

    public String errorMsg() {
        return errMsg;
    }
}
