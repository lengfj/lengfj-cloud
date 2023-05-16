package com.lengfj.cloud.common.springdoc;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.system.SystemUtil;
import com.github.xiaoymin.knife4j.spring.extension.Knife4jOpenApiCustomizer;
import com.lengfj.cloud.common.core.util.SpringContextUtil;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.util.List;

/**
 * swagger自动装配
 * @author lester
 * @version V1.0
 * @date 2021/11/23
 **/
@Slf4j
public class SpringDocAutoConfiguration implements WebMvcConfigurer {

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final String[] DEFAULT_EXCLUDE_PATH = { "/error", "/actuator/**" };

    private static final String[] BASE_PATH = { "/**" };

    private static final String[] PACKAGED_TO_MATCH = { "com.lengfj.cloud" };

    @Bean
    public GroupedOpenApi userApi(){
        return GroupedOpenApi.builder().group("lengfj.cloud")
                .pathsToMatch(BASE_PATH)
                .pathsToExclude(DEFAULT_EXCLUDE_PATH)
                .packagesToScan(PACKAGED_TO_MATCH).build();
    }

    /**
     * 根据@Tag 上的排序，写入x-order
     *
     * @return the global open api customizer
     */
    // @Bean
    // public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
    //     return openApi -> {
    //         log.info("=-=-=-=-=-=-==-=-=我的GlobalOpenApiCustomizer");
    //         if (openApi.getTags()!=null){
    //             openApi.getTags().forEach(tag -> {
    //                 Map<String,Object> map=new HashMap<>();
    //                 map.put("x-order", RandomUtil.randomInt(0,100));
    //                 tag.setExtensions(map);
    //             });
    //         }
    //         if(openApi.getPaths()!=null){
    //             openApi.addExtension("x-test123","333");
    //             openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(1,100));
    //         }
    //         String applicationName = SpringContextUtil.getApplicationName();
    //         String port = SpringContextUtil.getProperty("server.port");
    //         String contextPath = SpringContextUtil.getProperty("server.servlet.context-path");
    //         String address = SystemUtil.getHostInfo().getAddress();
    //         String url = "http://" + address + ":" + port + contextPath + "/doc.html";
    //
    //         log.info(applicationName + " apis doc : " + url);
    //     };
    // }

    @Bean
    public OpenAPI customOpenAPI() {

        //配置认证、请求头参数
        Components components = new Components();
        components
                .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                .addParameters("myHeader1", new Parameter().in("header").schema(new StringSchema()).name("myHeader1"))
                .addParameters("token", new HeaderParameter().required(true).name("token").description("token").schema(new StringSchema()).required(false))
        ;

        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("lengfjCloud系统API")
                        .version("1.0")
                        .contact(new Contact().name("lengfj").email("52111847@qq.com").url("https://github.com/lengfj"))
                        .description( "lengfjCloud脚手架")
                        .termsOfService("https://github.com/lengfj")
                        .license(new License().name("Apache 2.0").url("https://github.com/lengfj")));
    }

    /**
     * 通用拦截器排除设置，所有拦截器都会自动加springdoc-opapi相关的资源排除信息，不用在应用程序自身拦截器定义的地方去添加，算是良心解耦实现。
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        try {
            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
            if (registrations != null) {
                for (InterceptorRegistration interceptorRegistration : registrations) {
                    interceptorRegistration.excludePathPatterns("/springdoc**/**");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
