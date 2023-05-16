package com.lengfj.cloud.common.redis.lock;

/**
 * @Author by lester
 * @Date 2020/6/22
 */
public enum LockType {

	/** 可重入锁*/
	REENTRANT_LOCK,
	
	/** 公平锁*/
	FAIR_LOCK,
	
	/** 读锁*/
	READ_LOCK,
	
	/** 写锁*/
	WRITE_LOCK;
}
