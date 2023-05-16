package com.lengfj.cloud.common.redis.lock;

import com.lengfj.cloud.common.redis.constant.RedisLockConstants;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 * @author by lester
 * @date 2020/6/22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LenLock {

    /**
     * 锁的资源,key。支持spring el表达式
     */
    @AliasFor("value")
    String[] key() default {};

    /**
     * 锁的资源,key。支持spring el表达式
     */
    @AliasFor("key")
    String[] value() default {};

    /**
     * redis锁自定义前缀
     * keyPrefix为空 key组成规则为：lengfj-lock:类路径.方法(xxx):key
     * keyPrefix不为空 key组成规则为：lengfj-lock:keyPrefix:key
     */
    String keyPrefix() default "";

    /**
     * 获取锁失败，返回异常消息体
     */
    String lockFailureMsg() default RedisLockConstants.DEFAULT_LOCK_FAILURE_MSG;

    /**
     * 锁类型
     */
    LockType lockType() default LockType.REENTRANT_LOCK;

    /**
     * 获取锁等待时间，默认三秒
     */
    long waitTime() default RedisLockConstants.DEFAULT_WAIT_TIME;

    /**
     * 锁过期时间，默认10秒
     */
    long leaseTime() default RedisLockConstants.DEFAULT_LEASE_TIME;

    /**
     * 单位时间 （锁等待时间 和 锁过期时间 都使用此单位）
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
