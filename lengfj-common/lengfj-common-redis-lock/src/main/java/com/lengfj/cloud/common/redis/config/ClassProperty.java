package com.lengfj.cloud.common.redis.config;

import lombok.Data;

/**
 * @Author by lester
 * @Date 2020/6/9
 */
@Data
public class ClassProperty {

    private String className;

    public ClassProperty(String className){
        this.className = className;
    }
}
