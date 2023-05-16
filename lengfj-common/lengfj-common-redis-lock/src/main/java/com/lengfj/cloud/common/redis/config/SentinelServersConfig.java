package com.lengfj.cloud.common.redis.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author by lester
 * @Date 2020/6/11
 * @Desc 哨兵模式
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SentinelServersConfig extends BaseMasterSlaveServersConfig {

    private List<String> sentinelAddresses;

    private String masterName;

    private int database = 0;
}
