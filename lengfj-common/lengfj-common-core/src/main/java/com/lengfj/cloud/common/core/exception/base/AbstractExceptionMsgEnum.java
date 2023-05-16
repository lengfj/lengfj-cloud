package com.lengfj.cloud.common.core.exception.base;

/**
 * 对于无法使用messageSource的模块 可以继承此异常消息类，可以实现在枚举类中设置异常信息
 * @author lester
 * @version V1.0
 * @date 2021/10/20
 **/
public interface AbstractExceptionMsgEnum extends AbstractExceptionEnum{

    /**
     * 异常消息
     * @return
     */
    String getErrorMsg();
}
