package com.lengfj.cloud.common.job;

import cn.hutool.json.JSONUtil;
import com.lengfj.cloud.common.job.properties.XxlExecutorProperties;
import com.lengfj.cloud.common.job.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * xxl-Job自动装载
 *
 * @author lester
 * @version V1.0
 * @date 2021/10/21
 **/
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(XxlJobProperties.class)
@Slf4j
public class XxlJobAutoConfiguration {

    /**
     * 服务名称 包含 XXL_JOB_ADMIN 则说明是 Admin
     */
    private static final String XXL_JOB_ADMIN = "xxl-job-admin";

    /**
     * 配置xxl-job 执行器，提供自动发现 xxl-job-admin 能力
     * @param xxlJobProperties xxl 配置
     * @param environment 环境变量
     * @param discoveryClient 注册发现客户端
     * @return
     */
    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties, Environment environment,
                                                     DiscoveryClient discoveryClient) {
        String os = System.getProperty("os.name");
        //获取本机系统名称
        //默认 windows & mac 为本地开发环境，不启动注册xxl-job
        if (os != null && (os.toLowerCase().startsWith("win")
                || os.toLowerCase().startsWith("mac"))){
            //为本地启动注册xxl-job开了一个后门，若xxl.job.isDebug配置为true 则启动注册
            if (Boolean.FALSE.equals(xxlJobProperties.getIsDebug())){
                log.info(">>>>>>>>>>> xxl-job local[os.name:{}] debug run application. no register xxl-job...", os);
                log.info(">>>>>>>>>>> xxl-job local debug run application. If you need to start registration xxl-job. configuration properties [xxl.job.is-debug=true] ...");
                return null;
            }
        }
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        XxlExecutorProperties executor = xxlJobProperties.getExecutor();
        // 应用名默认为服务名
        String appName = executor.getAppname();
        if (!StringUtils.hasText(appName)) {
            appName = environment.getProperty("spring.application.name");
        }
        log.info(">>>>>>>>>>> xxl-job 配置：{}", JSONUtil.toJsonStr(xxlJobProperties));
        log.info(">>>>>>>>>>> xxl-job 应用名：{}",appName);
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setAddress(executor.getAddress());
        xxlJobSpringExecutor.setIp(executor.getIp());
        xxlJobSpringExecutor.setPort(executor.getPort());
        xxlJobSpringExecutor.setAccessToken(executor.getAccessToken());
        xxlJobSpringExecutor.setLogPath(executor.getLogPath() + "/" + appName);
        xxlJobSpringExecutor.setLogRetentionDays(executor.getLogRetentionDays());

        // 如果配置为空则获取注册中心的服务列表 "http://localhost:9080/xxl-job-admin"
        if (!StringUtils.hasText(xxlJobProperties.getAdmin().getAddresses())) {
            String serverList = discoveryClient.getServices().stream().filter(s -> s.contains(XXL_JOB_ADMIN))
                    .flatMap(s -> discoveryClient.getInstances(s).stream()).map(instance -> String
                            .format("http://%s:%s/%s", instance.getHost(), instance.getPort(), XXL_JOB_ADMIN))
                    .collect(Collectors.joining(","));
            log.info(">>>>>>>>>>> xxl-job 调度中心：{}",serverList);
            xxlJobSpringExecutor.setAdminAddresses(serverList);
        }
        else {
            xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        }

        return xxlJobSpringExecutor;
    }
}
