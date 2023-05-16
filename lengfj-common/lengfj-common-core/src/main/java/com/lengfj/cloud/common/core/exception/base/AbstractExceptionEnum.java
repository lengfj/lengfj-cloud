package com.lengfj.cloud.common.core.exception.base;

/**
 * 异常枚举格式规范，每个异常枚举都要实现这个接口
 * <p>
 * 为了在抛出BaseException的时候规范抛出的内容
 * <p>
 * BaseBusinessException抛出时必须为本接口的实现类
 * @author lester
 * @version V1.0
 * @date 2021/10/20
 **/
public interface AbstractExceptionEnum {

    /**
     * 异常码
     * @return
     */
    String getErrorCode();
}
