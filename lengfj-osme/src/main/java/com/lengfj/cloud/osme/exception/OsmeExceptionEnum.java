package com.lengfj.cloud.osme.exception;

import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;

/**
 * 模块异常枚举
 *
 * @author lester
 * @version V1.0
 * @date 2021/12/28
 **/
public enum OsmeExceptionEnum implements AbstractExceptionEnum {
    ORDER_NOT_FOUND("OSME-1001"),
    ORDER_STATE_NOT_MATCH("OSME-1002"),
    NOT_FOUND_PROCESSOR("OSME-1003"),
    FOUND_MORE_PROCESSOR("OSME-1004"),
    SYSTEM_ERROR("OSME-1005"),
    ;

    private String errorCode;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    OsmeExceptionEnum(String errorCode) {
        this.errorCode = errorCode;
    }
}
