package com.lengfj.cloud.osme.exception;

import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;

/**
 * todo
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/28 3:39 PM
 */
public class FsmException extends OsmeException{
    public FsmException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public FsmException(AbstractExceptionEnum exception, Object... objs) {
        super(exception, objs);
    }
}
