package com.lengfj.cloud.common.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.util.Map;

/**
 * 应用上下文获取工具类
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/2
 **/
@Slf4j
public class SpringContextUtil implements BeanFactoryPostProcessor,ApplicationContextAware {

    private static ConfigurableListableBeanFactory beanFactory;
    private static ApplicationContext applicationContext;

    public static String APP_NAME = "lengfj-cloud";

    public static String APP_START_BANNER =
            "\n\t\t\t>>>   ::::::::::::::::::::::::::::::::::::   <<<" +
            "\n\t\t\t>>>   :::::   Spring Boot 启动成功    :::::   <<<" +
            "\n\t\t\t>>>   ::::::::::::::::::::::::::::::::::::   <<<";

    private final static String PROPERTY_NAME = "spring.application.name";

    public SpringContextUtil() {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringContextUtil.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
        SpringContextUtil.APP_NAME = applicationContext.getEnvironment().getProperty(PROPERTY_NAME);
        log.info("set config app_name[{}],,, use: SpringContextUtil.APP_NAME", SpringContextUtil.APP_NAME);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ListableBeanFactory getBeanFactory() {
        return null == beanFactory ? applicationContext : beanFactory;
    }

    public static Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getBeanFactory().getBean(name, clazz);
    }


    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanFactory().getBeansOfType(type);
    }

    public static String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    public static String getProperty(String key) {
        return null == applicationContext ? null : applicationContext.getEnvironment().getProperty(key);
    }

    public static String getApplicationName() {
        return getProperty(PROPERTY_NAME);
    }

    public static String[] getActiveProfiles() {
        return null == applicationContext ? null : applicationContext.getEnvironment().getActiveProfiles();
    }

    public static String getActiveProfile() {
        String[] activeProfiles = getActiveProfiles();
        if(activeProfiles == null || activeProfiles.length == 0){
            return null;
        }
        return activeProfiles[0];
    }


}
