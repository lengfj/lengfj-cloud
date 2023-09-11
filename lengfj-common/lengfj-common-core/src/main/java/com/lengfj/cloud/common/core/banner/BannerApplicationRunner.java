package com.lengfj.cloud.common.core.banner;

import cn.hutool.core.thread.ThreadUtil;
import com.lengfj.cloud.common.core.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后数据启动成功日志
 * @author lengfj
 * @version 1.0
 * @date 2023/9/6
 **/
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        CompletableFuture.runAsync(() ->{
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("\n\t\t\t>>>   ::::::::::::::::::::::::::::::::::::   <<<" +
                    "\n\t\t\t>>>   :::::   {} 启动成功    :::::   <<<" +
                    "\n\t\t\t>>>   ::::::::::::::::::::::::::::::::::::   <<<", SpringContextUtil.getApplicationName());
        });
    }
}
