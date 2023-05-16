package com.lengfj.cloud.common.redis.lock;

import com.lengfj.cloud.common.core.exception.enums.DefaultExceptionEnum;
import com.lengfj.cloud.common.redis.exception.RedisLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author by lester
 * @Date 2020/6/22
 */
@Slf4j
@Aspect
@Component
public class LockAspectHandler {


    @Autowired
    private LockInfoProvider lockInfoProvider;
    @Autowired
    private LockFactory lockFactory;

    private final Map<String, RLock> currentThreadLock = new ConcurrentHashMap<>();

    @Around("@annotation(lockAction)")
    public Object around(ProceedingJoinPoint joinPoint, LenLock lockAction) throws Throwable {
        LockInfoProvider.LockInfo lockInfo = lockInfoProvider.getLockInfo(joinPoint, lockAction);
        //加锁
        tryLock(lockInfo);
        return joinPoint.proceed();
    }

    @AfterReturning(value = "@annotation(lockAction)")
    public void afterReturning(JoinPoint joinPoint, LenLock lockAction) {
        String currentLock = lockInfoProvider.getLockId(joinPoint,lockAction);
        boolean isRes = releaseLock(currentLock);
        cleanThreadLocalLock(currentLock);
        log.info("release redis lock result : {}",isRes);
    }

    @AfterThrowing(value = "@annotation(lockAction)", throwing = "ex")
    public void afterThrowing (JoinPoint joinPoint, LenLock lockAction, Throwable ex) throws Throwable {
        String currentLock = lockInfoProvider.getLockId(joinPoint,lockAction);
        boolean isRes = releaseLock(currentLock);
        cleanThreadLocalLock(currentLock);
        log.info("release redis lock result : {}",isRes);
        throw ex;
    }

    /**
     * 加锁
     */
    public void tryLock(LockInfoProvider.LockInfo lockInfo) throws Throwable {
        //获取锁，尝试加锁
        RLock lock = lockFactory.getLock(lockInfo.getLockName(), lockInfo.getLockType());
        if (!lock.tryLock(lockInfo.getWaitTime(),lockInfo.getLeaseTime(),lockInfo.getTimeUnit())){
            log.info("add redis lock failed [{}], waitTime [{}:{}]",
                    lockInfo.getLockName(), lockInfo.getWaitTime(), lockInfo.getTimeUnit().name());
            throw new RedisLockException(DefaultExceptionEnum.REDIS_ADD_LOCK_EXCEPTION,lockInfo.getLockFailureMsg());
        }
        currentThreadLock.put(lockInfoProvider.getLockId(lockInfo.getLockName()),lock);
        //得到锁，执行方法
        log.info("add redis lock success [{}]", lockInfo.getLockName());
    }

    /**
     *  释放锁
     */
    public boolean releaseLock(String currentLock){
        RLock lock = currentThreadLock.get(currentLock);
        if(Objects.isNull(lock)){
            throw new NullPointerException("释放锁失败，未获取到锁");
        }
        if (lock.isHeldByCurrentThread()){
            try{
                //异步释放锁
                if (!lock.forceUnlockAsync().get()){
                    log.error("释放redis锁失败，currentLock:{}", currentLock);
                    return false;
                }
            }catch (Exception e){
                log.error("释放redis锁失败，currentLock:{}", currentLock);
                return false;
            }
        }
        return true;
    }

    private void cleanThreadLocalLock(String currentLock){
        currentThreadLock.remove(currentLock);
    }
}
