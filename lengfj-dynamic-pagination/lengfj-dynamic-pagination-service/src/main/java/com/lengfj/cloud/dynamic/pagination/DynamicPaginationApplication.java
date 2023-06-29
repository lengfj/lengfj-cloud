package com.lengfj.cloud.dynamic.pagination;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import com.lengfj.cloud.common.springdoc.annotation.EnableSpringDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 动态分页列表组件工程启动类
 *
 * @author lester
 * @version V1.0
 * @date 2021/12/1
 **/
@Slf4j
@EnableAsync
@EnableDiscoveryClient
@EnableSpringDoc
@SpringBootApplication
public class DynamicPaginationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicPaginationApplication.class, args);
        log.info(SpringContextUtil.APP_START_BANNER);
    }
}
