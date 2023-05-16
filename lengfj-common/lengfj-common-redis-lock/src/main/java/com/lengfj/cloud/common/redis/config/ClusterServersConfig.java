package com.lengfj.cloud.common.redis.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author by lester
 * @Date 2020/6/11
 * @Desc 集群模式
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClusterServersConfig extends BaseMasterSlaveServersConfig {

    /**
     * redis 集群节点url列表
     */
    private List<String> nodeAddresses;

    /**
     *
     */
    private int scanInterval = 1000;;
}
