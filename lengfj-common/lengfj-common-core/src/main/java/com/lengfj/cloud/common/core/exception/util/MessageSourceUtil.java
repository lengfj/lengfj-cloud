package com.lengfj.cloud.common.core.exception.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;

import java.util.Locale;

/**
 * messageSource单例工具类
 * @author Lester
 * @date 2021/10/20
 * @version V1.0
 **/
public class MessageSourceUtil implements ApplicationContextAware {

    private MessageSourceUtil(){}

    private static volatile MessageSource messageSource;
    private static ApplicationContext applicationContext;


    public static MessageSource getInstance(){
        if (null == messageSource){
            synchronized (MessageSourceUtil.class){
                if (null == messageSource){
                    messageSource = applicationContext.getBean("messageSource", MessageSource.class);
                }
            }
        }
        return messageSource;
    }

    /**
     * 获取模板消息
     * @param code 消息code
     * @param args 替换参数
     * @param defaultMessage 如果没有获取到消息默认返回的消息
     * @return
     */
    public static String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage){
        return getInstance().getMessage(code, args, defaultMessage, Locale.CHINA);
    }

    /**
     * 获取模板消息
     * @param code 消息code
     * @param args 替换参数
     * @return
     */
    public static String getMessage(String code, @Nullable Object[] args){
        return getInstance().getMessage(code, args, code, Locale.CHINA);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MessageSourceUtil.applicationContext = applicationContext;
    }
}
