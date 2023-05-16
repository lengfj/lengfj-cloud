package com.lengfj.cloud.common.core.exception.enums;

import com.lengfj.cloud.common.core.exception.base.AbstractExceptionMsgEnum;

/**
 * common默认实现异常枚举
 * @author lester
 * @version V1.0
 * @date 2021/10/20
 **/
public enum DefaultExceptionEnum implements AbstractExceptionMsgEnum {

    DEFAULT_SYSTEM_EXCEPTION("COM-0001","系统异常"),
    /*
     * redis加锁异常
     */
    REDIS_ADD_LOCK_EXCEPTION("COM-0002","{0}"),
    REDIS_KEY_ANALYZE_EXCEPTION("COM-0003","key解析异常"),
    ASSERT_NOT_NULL_EXCEPTION("COM-0004","{0}不能为空"),
    ASSERT_NOT_ZERO_EXCEPTION("COM-0006","{0}不能为空且不能为0"),
    ID_EXCEPTION("COM-0007","{0}"),
    METHOD_ARGUMENT_NOT_VALID_EXCEPTION("COM-0008","{0}"),
    CONSTRAIN_VIOLATION_EXCEPTION("COM-0009","{0}"),
    BIND_EXCEPTION("COM-0010","{0}"),
    ;

    private String errorCode;
    private String errorMsg;

    DefaultExceptionEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
