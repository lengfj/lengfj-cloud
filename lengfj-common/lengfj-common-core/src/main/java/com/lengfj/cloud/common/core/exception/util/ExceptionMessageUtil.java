package com.lengfj.cloud.common.core.exception.util;

import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;
import com.lengfj.cloud.common.core.exception.base.AbstractExceptionMsgEnum;
import com.lengfj.cloud.common.core.i18n.MessageSourceUtil;

import java.text.MessageFormat;

/**
 * 异常消息的工具类
 *
 * @author lester
 * @version V1.0
 * @date 2021/10/21
 **/
public class ExceptionMessageUtil {

    /**
     * 获取消息
     * @param exceptionEnum 顶级异常枚举
     * @param args 参数
     * @return
     */
    public static String getMessage(AbstractExceptionEnum exceptionEnum, Object... args){
        if (exceptionEnum == null) return null;
        return exceptionEnum instanceof AbstractExceptionMsgEnum ?
                MessageFormat.format(((AbstractExceptionMsgEnum)exceptionEnum).getErrorMsg(), args) :
                MessageSourceUtil.getMessage(exceptionEnum.getErrorCode(), args);
    }

}
