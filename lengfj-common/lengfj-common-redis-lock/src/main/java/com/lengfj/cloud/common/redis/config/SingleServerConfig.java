package com.lengfj.cloud.common.redis.config;

import lombok.Data;

/**
 * @Author by lester
 * @Date 2020/6/11
 * @desc 单机redis配置
 */
@Data
public class SingleServerConfig extends BaseRedisConfig {

    /**
     * redis单机地址
     */
    private String address;

    /**
     * 从节点发布和订阅连接的最小空闲连接数
     */
    private int subscriptionConnectionMinimumIdleSize = 1;

    /**
     * 从节点发布和订阅连接池大小
     *
     */
    private int subscriptionConnectionPoolSize = 50;

    /**
     * 最小空闲连接数
     * 默认：32
     */
    private int connectionMinimumIdleSize = 10;

    /**
     * 连接池大小
     */
    private int connectionPoolSize = 64;

    /**
     * 数据库编号
     */
    private int database = 0;

    /**
     * DNS监测时间间隔，单位：毫秒
     */
    private long dnsMonitoringInterval = 5000;
}
