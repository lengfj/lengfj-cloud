package com.lengfj.cloud.common.redis;

import com.lengfj.cloud.common.redis.lock.LockAspectHandler;
import com.lengfj.cloud.common.redis.lock.LockFactory;
import com.lengfj.cloud.common.redis.lock.LockInfoProvider;
import com.lengfj.cloud.common.redis.util.LockUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

/**
 * redisson自动装配
 * @Author by lester
 * @Date 2020/6/22
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@ConditionalOnProperty(prefix = "spring.redisson", name = "enabled", havingValue = "true")
@Import(value = {LockAspectHandler.class, LockUtil.class})
public class RedissonAutoConfiguration {

    private final Logger log = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    @Resource
    private RedissonProperties redissonProperties;

    public Config configJson() throws IOException {
        File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getJson());
        return Config.fromJSON(file);
    }

    public Config configYaml() throws IOException {
        File file = ResourceUtils.getFile(redissonProperties.getConfigFile().getYaml());
        return Config.fromYAML(file);
    }

    @Bean
    public Config config() throws IOException {
        if (StringUtils.isNotEmpty(redissonProperties.getConfigFile().getYaml())){
            return configYaml();
        } else if (StringUtils.isNotEmpty(redissonProperties.getConfigFile().getJson())){
            return configJson();
        } else {
            Yaml yaml = new Yaml();
            String configYaml = yaml.dumpAsMap(redissonProperties.getConfig());
            log.debug("config is : {}", configYaml);
            return Config.fromYAML(configYaml);
        }
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(Config config) {
        log.info("create custom redissonClient, loading completed.");
        return Redisson.create(config);
    }

    @Bean
    public LockInfoProvider lockInfoProvider(){
        return new LockInfoProvider();
    }

    @Bean
    public LockFactory lockFactory(){
        return new LockFactory();
    }
}
