package com.lengfj.cloud.common.core.i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @author lengfj
 * @version 1.0
 * @date 2023/5/30
 **/
public class MessageConfig {

    @Value("${app.default.language:zh_CN}")
    private String defaultLanguage;

    /**
     * Spring的ResourceBundleMessageSource实现i18n
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * LocaleResolver转换本地语言
     * Spring 默认获取服务器的本地语言，改为通过从配置获取
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale(defaultLanguage));
        return resolver;
    }


    /**
     * 注解校验参数,指定用于解析验证消息的自定义 Spring 消息源
     * @param messageSource
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }
}
