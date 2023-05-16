package com.lengfj.cloud.common.core.exception;

import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;
import com.lengfj.cloud.common.core.exception.base.AbstractExceptionMsgEnum;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;
import com.lengfj.cloud.common.core.util.SpringContextUtil;

/**
 * 空指针异常
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/2
 **/
public class LengfjNullPointException extends BaseBusinessException {
    public LengfjNullPointException(AbstractExceptionEnum exception, Object... objs) {
        super(SpringContextUtil.APP_NAME, exception, objs);
    }

    public LengfjNullPointException(AbstractExceptionMsgEnum exception, Object... objs) {
        super(SpringContextUtil.APP_NAME, exception, objs);
    }

    public LengfjNullPointException(String errorCode, String errorMsg) {
        super(SpringContextUtil.APP_NAME, errorCode, errorMsg);
    }
}
