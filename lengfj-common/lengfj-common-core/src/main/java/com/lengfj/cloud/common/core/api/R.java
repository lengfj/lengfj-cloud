package com.lengfj.cloud.common.core.api;


import com.lengfj.cloud.common.core.constant.ApiConstant;
import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;
import com.lengfj.cloud.common.core.exception.util.ExceptionMessageUtil;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 统一API响应结果封装
 *
 * @author lester
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code = HttpStatus.OK.value();

    private String errorCode = ApiConstant.BUSINESS_SUCCESS;

    private T data;

    private String msg;


    private R(int code, String errorCode, T data, String msg) {
        this.code = code;
        this.errorCode = errorCode;
        this.data = data;
        this.msg = msg;
    }

    private R(String errorCode, T data, String msg) {
        this.errorCode = errorCode;
        this.data = data;
        this.msg = msg;
    }

    private R(T data, String msg) {
        this.data = data;
        this.msg = msg;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable R<?> result) {
        return Optional.ofNullable(result)
                .map(x -> x.code == HttpStatus.INTERNAL_SERVER_ERROR.value())
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(@Nullable R<?> result) {
        return !R.isSuccess(result);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(T data) {
        return data(data, null);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(T data, String msg) {
        return new R<>(data, msg);
    }

    /**
     * 返回R
     *
     * @param exceptionEnum 业务状态码
     * @param data 数据
     * @param args 参数
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> R<T> data(AbstractExceptionEnum exceptionEnum, T data, Object... args) {
        String message = ExceptionMessageUtil.getMessage(exceptionEnum, args);
        return new R<>(exceptionEnum.getErrorCode(), data, message);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @return R
     */
    public static <T> R<T> success(String msg) {
        return new R<>(null, msg);
    }

    /**
     * 返回R
     * 不建议使用，尽量使用AbstractExceptionEnum异常枚举返回异常信息
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(String msg) {
        return fail(ApiConstant.BUSINESS_FAIL,null,msg);
    }
    /**
     * 返回R
     * 不建议使用，尽量使用AbstractExceptionEnum异常枚举返回异常信息
     * @param errorCode 异常code
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(String errorCode, String msg) {
        return fail(errorCode,null,msg);
    }

    /**
     * 返回R
     * 不建议使用，尽量使用AbstractExceptionEnum异常枚举返回异常信息
     * @param errorCode 异常code
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(String errorCode, T data, String msg) {
        return new R<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),errorCode,data, msg);
    }

    /**
     * 返回R
     *
     * @param exceptionEnum 顶级异常枚举
     * @param args 替换参数
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(@NonNull AbstractExceptionEnum exceptionEnum, Object... args) {
        return fail(null, exceptionEnum, args);
    }

    /**
     * 返回R
     *
     * @param exceptionEnum 顶级异常枚举
     * @param args 替换参数
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> R<T> fail(T data, @NonNull AbstractExceptionEnum exceptionEnum, Object... args) {
        String message = ExceptionMessageUtil.getMessage(exceptionEnum, args);
        return new R<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), exceptionEnum.getErrorCode(), data, message);
    }

    /**
     * 返回R
     *
     * @param flag 成功状态
     * @return R
     */
    public static <T> R<T> status(boolean flag, T data, AbstractExceptionEnum exceptionEnum, Object... args) {
        return flag ? data(data) : fail(exceptionEnum, args);
    }

}

