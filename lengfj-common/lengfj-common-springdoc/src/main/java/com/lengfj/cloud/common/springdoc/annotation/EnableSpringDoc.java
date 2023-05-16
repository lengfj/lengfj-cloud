package com.lengfj.cloud.common.springdoc.annotation;

import com.lengfj.cloud.common.springdoc.SpringDocAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *  开启swagger
 * @author lester
 * @version V1.0
 * @date 2021/11/23
 **/
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringDocAutoConfiguration.class})
public @interface EnableSpringDoc {
}
