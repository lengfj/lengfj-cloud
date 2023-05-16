package com.lengfj.cloud.common.redis.config;

import lombok.Data;

/**
 * @Author by lester
 * @Date 2020/6/9
 * @desc redisson 配置 https://github.com/redisson/redisson/wiki/2.-Configuration#22-declarative-configuration
 */
@Data
public class BaseRedisConfig {

    /**
     * 描述：如果池连接一段时间没有使用，并且当前连接数量大于最小空闲连接池大小，那么它将关闭并从池中删除
     * 单位：毫秒
     * 默认值：10000
     */
    private int idleConnectionTimeout = 10000;
    /**
     * 描述：在连接到任何Redis服务器期间超时毫秒。
     * 默认值：10000
     */
    private int connectTimeout = 10000;

    /**
     * 描述：Redis服务器响应超时(毫秒)。当Redis命令成功发送后，开始倒计时。
     * 默认值：3000
     */
    private int timeout = 3000;

    /**
     * 描述：如果Redis命令在重试后不能发送到Redis服务器，将会抛出错误。但如果发送成功，则会启动超时。
     * 默认值：3
     */
    private int retryAttempts = 3;

    /**
     * 描述：时间间隔(毫秒)后，另一个尝试发送Redis命令将被执行。
     * 默认值：1500
     */
    private int retryInterval = 1500;

    /**
     * redis认证密码
     */
    private String password;

    /**
     * 每个订阅连接的订阅限制
     */
    private int subscriptionsPerConnection = 5;
}
