package com.lengfj.cloud.osme.exception;

import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;

/**
 * 模块异常
 *
 * @author lester
 * @version V1.0
 * @date 2021/12/28
 **/
public class OsmeException extends BaseBusinessException {
    public OsmeException(String errorCode, String errorMsg) {
        super("osme", errorCode, errorMsg);
    }

    public OsmeException(AbstractExceptionEnum exception, Object... objs) {
        super("osme", exception, objs);
    }
}
