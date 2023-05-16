package com.lengfj.cloud.common.core.exception.base;

import com.lengfj.cloud.common.core.exception.util.ExceptionMessageUtil;
import lombok.EqualsAndHashCode;

/**
 * 业务异常基类
 * @author lester
 */
@EqualsAndHashCode(callSuper = true)
public class BaseBusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 异常的模块名称
     */
    private String moduleName;

    /**
     * 留了一个口子，可以自己指定，但业务异常非必要禁止使用
     * @param moduleName 模块名
     * @param errorCode 异常码
     * @param errorMsg 异常信息
     */
    public BaseBusinessException(String moduleName, String errorCode, String errorMsg) {
        super(errorMsg);
        this.moduleName = moduleName;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * @param moduleName 模块名
     * @param exception 异常枚举
     * @param objs 参数
     */
    public BaseBusinessException(String moduleName, AbstractExceptionEnum exception, Object... objs) {
        super(ExceptionMessageUtil.getMessage(exception, objs));
        this.moduleName = moduleName;
        this.errorCode = exception.getErrorCode();
        this.errorMsg = ExceptionMessageUtil.getMessage(exception, objs);
    }

    /**
     * 异常体系继承自AbstractExceptionMsgEnum，使用此构造方法
     * @param moduleName 模块名
     * @param exception 异常枚举
     * @param objs 参数
     */
    public BaseBusinessException(String moduleName, AbstractExceptionMsgEnum exception, Object... objs) {
        super(ExceptionMessageUtil.getMessage(exception, objs));
        this.moduleName = moduleName;
        this.errorCode = exception.getErrorCode();
        this.errorMsg = ExceptionMessageUtil.getMessage(exception, objs);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getModuleName() {
        return moduleName;
    }

    @Override
    public String toString() {
        return "{" +
                "\"exceptionName\": \""+this.getClass().getSimpleName()+"\"," +
                "\"moduleName\": \""+this.moduleName+"\"," +
                "\"errorCode\": \""+this.errorCode+"\"," +
                "\"errorMsg\": \""+this.errorMsg+"\"" +
                "}";
    }
}
