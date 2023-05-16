package com.lengfj.cloud.common.redis.lock;

import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;


/**
 * redisson 锁工厂
 *
 * @author lester
 * @version V1.0
 * @date 2021/10/29
 **/
public class LockFactory {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取redis锁类型
     * @param key 锁的key
     * @param lockType 加锁类型
     * @return 实现的锁
     */
    public RLock getLock(String key, LockType lockType) {
        switch (lockType) {
            case REENTRANT_LOCK:
                return redissonClient.getLock(key);
            case FAIR_LOCK:
                return redissonClient.getFairLock(key);
            case READ_LOCK:
                return redissonClient.getReadWriteLock(key).readLock();
            case WRITE_LOCK:
                return redissonClient.getReadWriteLock(key).writeLock();
            default:
                throw new RuntimeException("do not support lock type:" + lockType.name());
        }
    }
}
