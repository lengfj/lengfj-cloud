package com.lengfj.cloud.common.redis.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;

/**
 * 非单机部署的配置父类
 * @Author by lester
 * @Date 2020/6/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseMasterSlaveServersConfig extends BaseRedisConfig {

    /**
     * 负载均衡算法类的选择
     * 在多Redis服务节点的环境里，可以选用以下几种负载均衡方式选择一个节点：
     * org.redisson.connection.balancer.WeightedRoundRobinBalancer - 权重轮询调度算法
     * org.redisson.connection.balancer.RoundRobinLoadBalancer - 轮询调度算法
     * org.redisson.connection.balancer.RandomLoadBalancer - 随机调度算法
     * 默认值：org.redisson.connection.balancer.RoundRobinLoadBalancer
     */
//    private ClassProperty loadBalancer = new ClassProperty(RoundRobinLoadBalancer.class.getName());
//    private LoadBalancer loadBalancer = new RoundRobinLoadBalancer();
//    private String loadBalancer = "org.redisson.connection.balancer.RoundRobinLoadBalancer";

    /**
     * 读取操作的负载均衡模式
     * 注：在<strong>从服务节点</strong>里读取的数据说明已经至少有两个节点保存了该数据，确保了数据的高可用性
     *
     * 设置读取操作选择节点的模式。可用值为
     * 1. SLAVE - 只在从服务器节点里读取
     * 2. MASTER - 只在主服务器节点读取
     * 3. MASTER_SLAVE - 在主从服务节点都可以读取
     * 默认值：SLAVE
     */
    private ReadMode readMode = ReadMode.SLAVE;

    /**
     * 订阅操作的负载均衡模式
     * 设置订阅操作选择节点的模式。 可用值为：
     *  SLAVE - 只在从服务节点里订阅。
     *  MASTER - 只在主服务节点里订阅。
     * 默认值：SLAVE
     */
    private SubscriptionMode subscriptionMode = SubscriptionMode.SLAVE;

    /**
     * 从节点发布和订阅连接的最小空闲连接数
     * 多从节点的环境里，每个 从服务节点里用于发布和订阅连接的最小保持连接数（长连接）。
     * Redisson内部经常通过发布和订阅来实现许多功能。长期保持一定数量的发布订阅连接是必须的。
     * 默认值：1
     */
    private int subscriptionConnectionMinimumIdleSize = 1;

    /**
     * 从节点发布和订阅连接池大小
     * 多从节点的环境里，每个 从服务节点里用于发布和订阅连接的连接池最大容量。连接池的连接数量自动弹性伸缩。
     * 默认值：50
     */
    private int subscriptionConnectionPoolSize = 50;

    /**
     * 从节点最小空闲连接数
     * 多从节点的环境里，每个 从服务节点里用于普通操作（非 发布和订阅）的最小保持连接数（长连接）。长期保持一定数量的连接有利于提高瞬时读取反映速度。
     * 默认值：32
     */
    private int slaveConnectionMinimumIdleSize = 32;

    /**
     * 从节点连接池大小
     * 多从节点的环境里，每个 从服务节点里用于普通操作（非 发布和订阅）连接的连接池最大容量。连接池的连接数量自动弹性伸缩。
     * 默认值：64
     */
    private int slaveConnectionPoolSize = 64;

    /**
     * 主节点连接池大小
     * 多主节点的环境里，每个 主节点的连接池最大容量。连接池的连接数量自动弹性伸缩。
     * 默认值：64
     */
    private int masterConnectionPoolSize = 64;


}
