package com.lengfj.cloud.common.redis.constant;

import com.lengfj.cloud.common.redis.lock.LockType;

import java.util.concurrent.TimeUnit;

/**
 * redis lock模块常量
 *
 * @author lester
 * @version V1.0
 * @date 2021/10/29
 **/
public interface RedisLockConstants {

    /**
     * 加锁失败消息
     */
    String DEFAULT_LOCK_FAILURE_MSG = "重复提交，请稍后再试";
    /**
     * 获取超时等待时间
     */
    long DEFAULT_WAIT_TIME = 3000L;
    /**
     * 锁过期时间
     */
    long DEFAULT_LEASE_TIME = 10000L;
    /**
     * 默认超时单位
     */
    TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;
    /**
     * 默认锁类型
     */
    LockType DEFAULT_LOCK_TYPE = LockType.REENTRANT_LOCK;
}
