package com.lengfj.cloud.osme;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import com.lengfj.cloud.common.springdoc.annotation.EnableSpringDoc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 脚手架工程启动类
 *
 * @author lester
 * @version V1.0
 * @date 2021/12/1
 **/
@Slf4j
@EnableAsync
@EnableSpringDoc
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.lengfj.cloud.osme.dao"})
public class OsmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsmeApplication.class, args);
        log.info(SpringContextUtil.APP_START_BANNER);
    }
}
