package com.lengfj.cloud.common.redis;

import com.lengfj.cloud.common.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * redis 自动配置
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/1
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring.redis", name = "host")
@Import(value = {RedisUtil.class})
public class RedisAutoConfiguration {
    /**
     * 缓存生存时间
     */
    private Duration timeToLive = Duration.ofDays(1);

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //redis缓存配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.timeToLive)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .disableCachingNullValues();
        //缓存配置map
        Map<String,RedisCacheConfiguration> cacheConfigurationMap=new HashMap<>();
        //自定义缓存名，后面使用的@Cacheable的CacheName
        cacheConfigurationMap.put("users",config);
        cacheConfigurationMap.put("default",config);
        //根据redis缓存配置和reid连接工厂生成redis缓存管理器
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .build();
        log.info("create custom redisCacheManager, loading completed.");
        return redisCacheManager;
    }

    /**
     * redisTemplate模板提供给其他类对redis数据库进行操作
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        log.info("create custom redisTemplate, loading completed.");
        return redisTemplate;
    }

    /**
     * redis键序列化使用StringRedisSerializer
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }
    /**
     * redis值序列化使用json序列化器 --要兼容老系统这里不能用
     * redis值序列化使用StringRedisSerializer
     */
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
//        return new Jackson2JsonRedisSerializer();
//        return new GenericFastJsonRedisSerializer();
//        return new StringRedisSerializer();
    }

    /**
     * 缓存键自动生成器
     * @return
     */
    @Bean
    public KeyGenerator myKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

}
