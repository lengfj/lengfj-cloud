package com.lengfj.cloud.common.mybatis;

import com.lengfj.cloud.common.mybatis.config.MybatisPlusMetaObjectHandler;
import org.springframework.context.annotation.Bean;

/**
 * mybatis自动配置
 * @author lester
 * @date 2022/2/18 1:40 下午
 */
public class MybatisAutoConfiguration {

    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler(){
        return new MybatisPlusMetaObjectHandler();
    }

}
