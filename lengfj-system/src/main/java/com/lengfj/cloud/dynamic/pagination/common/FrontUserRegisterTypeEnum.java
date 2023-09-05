package com.lengfj.cloud.dynamic.pagination.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lengfj
 * @version 1.0
 * @date 2023/7/4
 **/
@Getter
@AllArgsConstructor
public enum FrontUserRegisterTypeEnum {
    /**
     * 邮箱注册
     */
    EMAIL("email", "邮箱"),

    /**
     * 手机号注册
     */
    PHONE("phone", "手机号");

    @Getter
    @EnumValue
    @JsonValue
    private final String code;

    @Getter
    private final String desc;
}
