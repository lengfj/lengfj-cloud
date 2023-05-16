package com.lengfj.cloud.common.core.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;
import com.lengfj.cloud.common.core.exception.enums.DefaultExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Optional;

/**
 * 全局异常处理
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/12
 **/
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandle {

    /**
     * 系统级异常
     * @param request
     * @param response
     * @param ex
     * @throws IOException
     */
    @ExceptionHandler({Exception.class})
    public void globalException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        //控制台输出异常，方便排错
        log.error("request:\n" + request, ex);

        R<Object> r = R.fail(DefaultExceptionEnum.DEFAULT_SYSTEM_EXCEPTION);
        JSONObject result = JSON.parseObject(JSON.toJSONString(r));
        //调试信息
        result.put("debugMessage", ex.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result.toJSONString());
    }

    /**
     * 业务异常
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {BaseBusinessException.class})
    public void globalException(HttpServletRequest request, HttpServletResponse response, BaseBusinessException ex) throws IOException {
        R<Object> r = R.fail(ex.getErrorCode(), ex.getErrorMsg());
        JSONObject result = JSON.parseObject(JSON.toJSONString(r));
        //模块名
        result.put("module",ex.getModuleName());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result.toJSONString());
    }

    /**
     * Validator验证异常
     * @param request
     * @param response
     * @param ex
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void globalException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) throws IOException {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        R<Object> r = R.fail(DefaultExceptionEnum.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, fieldError.getDefaultMessage());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(r));
    }

    /**
     * Validator验证异常
     * @param request
     * @param response
     * @param ex
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public void globalException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException ex) throws IOException {
        Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
        String message = first.isPresent() ? first.get().getMessage() : "系统错误";
        R<Object> r = R.fail(DefaultExceptionEnum.CONSTRAIN_VIOLATION_EXCEPTION, message);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(r));
    }

    /**
     * Validator验证异常
     * @param request
     * @param response
     * @param ex
     */
    @ExceptionHandler(BindException.class)
    public void globalException(HttpServletRequest request, HttpServletResponse response, BindException ex) throws IOException {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        R<Object> r = R.fail(DefaultExceptionEnum.BIND_EXCEPTION, fieldError.getDefaultMessage());


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(r));
    }

}
