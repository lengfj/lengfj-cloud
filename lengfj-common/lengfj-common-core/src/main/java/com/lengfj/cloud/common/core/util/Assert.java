package com.lengfj.cloud.common.core.util;

import com.lengfj.cloud.common.core.exception.LengfjIllegalArgumentException;
import com.lengfj.cloud.common.core.exception.LengfjNullPointException;
import com.lengfj.cloud.common.core.exception.LengfjZeroException;
import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;
import com.lengfj.cloud.common.core.exception.enums.DefaultExceptionEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 业务断言类
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/2
 **/
public abstract class Assert {


    /**
     * 断言对象为true，不为true抛出异常
     * @param expression
     * @param exceptionEnum
     * @param args
     * @throws LengfjIllegalArgumentException
     */
    public static void isTrue(boolean expression, AbstractExceptionEnum exceptionEnum, Object... args) throws LengfjIllegalArgumentException {
        if (!expression) {
            throw new LengfjIllegalArgumentException(exceptionEnum, args);
        }
    }

    /**
     * 断言对象为false，不为false抛出异常
     * @param expression
     * @param exceptionEnum
     * @param args
     */
    public static void isFalse(boolean expression, AbstractExceptionEnum exceptionEnum, Object... args) {
        if (expression) {
            throw new LengfjIllegalArgumentException(exceptionEnum, args);
        }
    }

    /**
     * 断言对象不为空,若对象为空则报异常
     * @param obj 待校验对象
     * @param exceptionEnum 异常消息
     * @param args 参数
     * @throws LengfjNullPointException
     */
    public static void notNull(Object obj, AbstractExceptionEnum exceptionEnum, Object... args) throws LengfjNullPointException {
        if(obj == null){
            throw new LengfjNullPointException(exceptionEnum, args);
        }
    }

    /**
     * 断言对象不为空,若对象为空则报异常
     * @param obj 待校验对象
     */
    public static void notNull(Object obj, Object... arg) throws LengfjNullPointException {
        notNull(obj, DefaultExceptionEnum.ASSERT_NOT_NULL_EXCEPTION, arg);
    }



    /**
     * 断言对象不为空,若对象为空则报异常
     * @param obj 待校验对象
     */
    public static void notNull(Object obj) throws LengfjNullPointException {
        notNull(obj, "参数");
    }

    /**
     * 断言数字不能为零，若数字为零则报异常
     * @param num 待校验数字
     * @param exceptionEnum 异常消息
     * @param args 参数
     */
    public static void notZero(Integer num, AbstractExceptionEnum exceptionEnum, Object... args) throws LengfjZeroException {
        notNull(num);
        if(num.intValue() == 0){
            throw new LengfjZeroException(exceptionEnum, args);
        }
    }

    /**
     * 断言数字不能为零，若数字为零则报异常
     * @param num 待校验数字
     * @param arg 异常消息
     */
    public static void notZero(Integer num, Object... arg) throws LengfjZeroException {
        notZero(num, DefaultExceptionEnum.ASSERT_NOT_ZERO_EXCEPTION, arg);
    }


    /**
     * 断言数字不能为零，若数字为零则报异常
     * @param num 待校验数字
     */
    public static void notZero(Integer num) throws LengfjZeroException {
        notZero(num,"参数");
    }

    /**
     * 断言字符串不能为空，若字符串为空则报异常
     * 调用StringUtils.isBlank方法
     * @param string 待校验字符串
     * @param exceptionEnum 异常消息
     * @param args 参数
     * @throws LengfjNullPointException
     */
    public static void notEmpty(String string, AbstractExceptionEnum exceptionEnum, Object... args) throws LengfjNullPointException {
        if(StringUtils.isBlank(string)) {
            throw new LengfjNullPointException(exceptionEnum, args);
        }
    }

    /**
     * 断言字符串不能为空，若字符串为空则报异常
     * 调用StringUtils.isBlank方法
     * @param string 待校验字符串
     * @param arg 参数
     * @throws LengfjNullPointException
     */
    public static void notEmpty(String string, Object... arg) throws LengfjNullPointException {
        notEmpty(string, DefaultExceptionEnum.ASSERT_NOT_NULL_EXCEPTION,arg);
    }

    /**
     * 断言字符串不能为空，若字符串为空则报异常
     * 调用StringUtils.isBlank方法
     * @param string 待校验字符串
     * @throws LengfjNullPointException
     */
    public static void notEmpty(String string) throws LengfjNullPointException {
        notEmpty(string,"参数");
    }

    /**
     * 断言集合不能为空
     * (null or size() == 0)  is null
     *
     * @param collection 待校验列表
     * @param exceptionEnum 异常信息
     * @param args 参数
     * @throws LengfjNullPointException
     */
    public static <T> void notEmpty(Collection<T> collection, AbstractExceptionEnum exceptionEnum, Object... args) throws LengfjNullPointException {
        if (null == collection || collection.isEmpty()){
            throw new LengfjNullPointException(exceptionEnum, args);
        }
    }

    /**
     * 断言集合不能为空
     * (null or size() == 0)  is null
     *
     * @param collection 待校验列表
     * @param arg 参数
     * @throws LengfjNullPointException
     */
    public static <T> void notEmpty(Collection<T> collection, Object... arg){
        notEmpty(collection,DefaultExceptionEnum.ASSERT_NOT_NULL_EXCEPTION,arg);
    }

    /**
     * 断言集合不能为空
     * (null or size() == 0)  is null
     *
     * @param collection 待校验列表
     * @throws LengfjNullPointException
     */
    public static <T> void notEmpty(Collection<T> collection){
        notEmpty(collection,"");
    }
}
