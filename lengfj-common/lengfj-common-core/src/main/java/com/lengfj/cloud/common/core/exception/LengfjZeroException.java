package com.lengfj.cloud.common.core.exception;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;
import com.lengfj.cloud.common.core.exception.base.AbstractExceptionMsgEnum;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;

/**
 * 不能为0异常
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/2
 **/
public class LengfjZeroException extends BaseBusinessException {

    public LengfjZeroException(String errorCode, String errorMsg) {
        super(SpringContextUtil.APP_NAME, errorCode, errorMsg);
    }

    public LengfjZeroException(AbstractExceptionEnum exception, Object... objs) {
        super(SpringContextUtil.APP_NAME, exception, objs);
    }

    public LengfjZeroException(AbstractExceptionMsgEnum exception, Object... objs) {
        super(SpringContextUtil.APP_NAME, exception, objs);
    }
}
