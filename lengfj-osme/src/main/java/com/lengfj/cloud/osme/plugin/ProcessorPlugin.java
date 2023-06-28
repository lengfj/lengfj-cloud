package com.lengfj.cloud.osme.plugin;

import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插件注解
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 4:14 PM
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface ProcessorPlugin {

    /**
     * 指定状态，state不能同时存在
     */
    String[] state() default {};
    /**
     * 订单操作事件
     */
    String event();
    /**
     * 业务
     */
    String[] bizCode() default {};
    /**
     * 场景
     */
    String[] sceneId() default {};
}
