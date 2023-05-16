package com.lengfj.cloud.common.redis.config;

import lombok.Data;

import java.util.Set;

/**
 * @Author by lester
 * @Date 2020/6/11
 * @desc 主从模式
 */
@Data
public class MasterSlaveServersConfig extends BaseMasterSlaveServersConfig {

    /**
     * redis 从节点集合
     */
    private Set<String> slaveAddresses;

    /**
     * Redis 主节点地址
     */
    private String masterAddress;

    /**
     * 所用redis数据库索引
     */
    private int database = 0;
}
