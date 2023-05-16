package com.lengfj.cloud.common.redis.config;

import lombok.Data;

import java.util.List;

/**
 * @Author by lester
 * @Date 2020/6/11
 * @Desc 主从复制模式
 */
@Data
public class ReplicatedServersConfig extends BaseMasterSlaveServersConfig {

    /**
     * Replication group node urls list
     */
    private List<String> nodeAddresses;

    /**
     * Replication group scan interval in milliseconds
     */
    private int scanInterval = 1000;

    /**
     * Database index used for Redis connection
     */
    private int database = 0;
}
