package com.lengfj.cloud.common.core.banner;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * banner自动配置
 * @author lengfj
 * @version 1.0
 * @date 2023/9/6
 **/
@AutoConfiguration
public class BannerAutoConfiguration {

    @Bean
    public BannerApplicationRunner bannerApplicationRunner(){
        return new BannerApplicationRunner();
    }

}
