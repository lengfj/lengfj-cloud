package com.lengfj.cloud.monitor.admin;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lester
 * @date 2022/2/11 6:14 下午
 */
@Slf4j
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MonitorAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorAdminApplication.class,args);
        log.info(SpringContextUtil.APP_START_BANNER);
    }
}
