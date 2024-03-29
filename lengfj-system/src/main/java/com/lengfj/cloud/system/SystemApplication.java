package com.lengfj.cloud.system;

import cn.dev33.satoken.SaManager;
import com.lengfj.cloud.common.springdoc.annotation.EnableSpringDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lengfj
 * @version 1.0
 * @date 2023/5/10
 **/
@Slf4j
@EnableSpringDoc
@SpringBootApplication
@EnableDiscoveryClient
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
