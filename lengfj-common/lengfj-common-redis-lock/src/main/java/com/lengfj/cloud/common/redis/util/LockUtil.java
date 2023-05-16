package com.lengfj.cloud.common.redis.util;

import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;
import com.lengfj.cloud.common.core.exception.enums.DefaultExceptionEnum;
import com.lengfj.cloud.common.redis.constant.RedisLockConstants;
import com.lengfj.cloud.common.redis.exception.RedisLockException;
import com.lengfj.cloud.common.redis.lock.LockAspectHandler;
import com.lengfj.cloud.common.redis.lock.LockInfoProvider;
import com.lengfj.cloud.common.redis.lock.LockType;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁工具类
 * @author lester
 * @date 2022/3/8 5:03 下午
 */
@Slf4j
@Component
public class LockUtil {

    @Resource
    private LockAspectHandler lockAspectHandler;

    private final ThreadLocal<String> currentLockMap = new ThreadLocal<>();

    /**
     * 加锁
     * @param lockName 锁名称
     */
    public void lock(String lockName){
        lock(lockName, RedisLockConstants.DEFAULT_WAIT_TIME, RedisLockConstants.DEFAULT_LEASE_TIME);
    }

    /**
     * 加锁
     * @param lockName 锁名称
     * @param waitTime 获取锁等待时间
     * @param leaseTime 锁过期时间
     */
    public void lock(String lockName, long waitTime, long leaseTime){
        lock(RedisLockConstants.DEFAULT_LOCK_TYPE,
                lockName,
                waitTime,
                leaseTime);
    }

    /**
     * 加锁
     * @param lockType 锁类型 {@link LockType}
     * @param lockName 锁名称
     * @param waitTime 获取锁等待时间
     * @param leaseTime 锁过期时间
     */
    public void lock(LockType lockType, String lockName, long waitTime, long leaseTime){
        lock(lockType,
                lockName,
                waitTime,
                leaseTime,
                RedisLockConstants.DEFAULT_TIME_UNIT);
    }

    /**
     * 加锁
     * @param lockType 锁类型 {@link LockType}
     * @param lockName 锁名称
     * @param waitTime 获取锁等待时间
     * @param leaseTime 锁过期时间
     * @param timeUnit 时间单位
     */
    public void lock(LockType lockType, String lockName, long waitTime, long leaseTime, TimeUnit timeUnit){
        lock(lockType,
                lockName,
                waitTime,
                leaseTime,
                timeUnit, RedisLockConstants.DEFAULT_LOCK_FAILURE_MSG);
    }

    /**
     * 加锁
     * @param lockType 锁类型 {@link LockType}
     * @param lockName 锁名称
     * @param waitTime 获取锁等待时间
     * @param leaseTime 锁过期时间
     * @param timeUnit 时间单位
     * @param lockFailureMsg 获取锁失败，返回异常消息体
     */
    public void lock(LockType lockType, String lockName, long waitTime, long leaseTime, TimeUnit timeUnit, String lockFailureMsg){
        LockInfoProvider.LockInfo lockInfo = new LockInfoProvider.LockInfo(
                lockType,
                lockName,
                waitTime,
                leaseTime,
                timeUnit,
                lockFailureMsg);
        lock0(lockInfo);
    }

    /**
     * 加锁
     * @param lockInfo 锁信息
     */
    public void lock0(LockInfoProvider.LockInfo lockInfo){
        try {
            currentLockMap.set(lockInfo.getLockName());
            lockAspectHandler.tryLock(lockInfo);
        } catch (BaseBusinessException e){
            currentLockMap.remove();
            throw e;
        } catch(Throwable e) {
            currentLockMap.remove();
            log.error("获取锁异常",e);
            throw new RedisLockException(DefaultExceptionEnum.REDIS_ADD_LOCK_EXCEPTION,lockInfo.getLockFailureMsg());
        }
    }

    /**
     * 释放锁
     * 从当前线程中获取锁名称
     * @return 是否释放成功
     */
    public boolean releaseLock(){
        String currentLock = currentLockMap.get();
        if (currentLock == null || "".equals(currentLock)){
            log.error("释放redis锁失败，未获取到当前线程的锁名称");
            return false;
        }
        return releaseLock(currentLock);
    }

    /**
     * 释放锁
     * @param lockName 锁名称
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockName){
        currentLockMap.remove();
        return lockAspectHandler.releaseLock(lockName);
    }

}
