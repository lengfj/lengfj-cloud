package com.lengfj.cloud.common.redis.exception;


import com.lengfj.cloud.common.core.exception.base.AbstractExceptionMsgEnum;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;

/**
 * redis lock模块异常
 *
 * @author lester
 * @version V1.0
 * @date 2021/10/29
 **/
public class RedisLockException extends BaseBusinessException {

    public RedisLockException(AbstractExceptionMsgEnum exception, Object... objs) {
        super("lengfj-common-redis-lock", exception, objs);
    }

    public RedisLockException(String errorCode, String errorMsg) {
        super("lengfj-common-redis-lock", errorCode, errorMsg);
    }
}
