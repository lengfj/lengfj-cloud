package com.lengfj.cloud.leaf;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 基于美团Leaf的分布式ID生成项目启动类
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/5 11:22 AM
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class LeafApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeafApplication.class,args);
        log.info(SpringContextUtil.APP_START_BANNER);
    }

}
