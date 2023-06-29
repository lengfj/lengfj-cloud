package com.lengfj.cloud.dynamic.pagination.starter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列表页面查询注解
 * 数据表
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/26
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface QueryPage {

    /**
     * 页面code；如填写以注解为准，未填写以PageParam为准
     */
    String pageCode() default "";
}
