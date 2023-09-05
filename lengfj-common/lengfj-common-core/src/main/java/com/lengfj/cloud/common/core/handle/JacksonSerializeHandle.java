package com.lengfj.cloud.common.core.handle;

import com.lengfj.cloud.common.core.util.serialize.BaseJacksonUtil;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * jackson全局序列化配置
 * @author lengfj
 * @version 1.0
 * @date 2023/9/4
 **/
@Configuration
public class JacksonSerializeHandle {

    /**
     * jackson全局配置
     * @author lengfj
     * @date 2023/9/4 16:17
     * @return {@link Jackson2ObjectMapperBuilderCustomizer}
     * @version main
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return BaseJacksonUtil.CUSTOMIZER;
    }

}
