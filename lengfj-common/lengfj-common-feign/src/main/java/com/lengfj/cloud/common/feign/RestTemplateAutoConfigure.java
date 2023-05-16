package com.lengfj.cloud.common.feign;


import com.lengfj.cloud.common.feign.interceptor.RestTemplateLoggingRequestInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


/**
 * rest template 自动配置
 *
 * @author lester
 * @version V1.0
 * @date 2021/12/1
 **/
public class RestTemplateAutoConfigure {

    /**
     * 初始化请求模板
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                        ClientHttpRequestFactory simpleClientHttpRequestFactory) {
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory))
                .additionalInterceptors(new RestTemplateLoggingRequestInterceptor())
                .build();
    }

    /**
     * 初始化连接工厂
     */
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //连接超时
        factory.setConnectTimeout(15000);
        //读超时
        factory.setReadTimeout(5000);
        return factory;
    }
}
