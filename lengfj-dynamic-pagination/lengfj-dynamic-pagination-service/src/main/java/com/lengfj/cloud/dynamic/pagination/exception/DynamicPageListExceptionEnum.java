package com.lengfj.cloud.dynamic.pagination.exception;


import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;

/**
 * 动态分页列表组件模块异常枚举
 * BDPL --- business dynamic page list
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 2:35 PM
 */
public enum DynamicPageListExceptionEnum implements AbstractExceptionEnum {
    BDPL_SYSTEM_ERROR("BDPL-1001"),
    BDPL_PAGE_REPEAT_ERROR("BDPL-1002"),
    BDPL_PAGE_FIELD_REPEAT_ERROR("BDPL-1003"),

    ;

    private String errorCode;

    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    DynamicPageListExceptionEnum(String errorCode){
        this.errorCode = errorCode;
    }
}
