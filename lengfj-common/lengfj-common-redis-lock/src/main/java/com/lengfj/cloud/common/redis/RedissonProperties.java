package com.lengfj.cloud.common.redis;

import com.lengfj.cloud.common.redis.config.Config;
import com.lengfj.cloud.common.redis.config.ConfigFile;
import lombok.Data;
import org.redisson.spring.cache.CacheConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * redisson配置
 * @Author by lester
 * @Date 2020/6/12
 */
@Data
@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

    /**
     * 是否开启redisson
     */
    private Boolean enabled = true;

    /**
     * 属性的配置方式
     */
    private Config config;

    /**
     * 文件的配置方式
     */
    private ConfigFile configFile = new ConfigFile();

    /**
     * 方法注解缓存的配置
     */
    private Map<String, CacheConfig> caches;
}
