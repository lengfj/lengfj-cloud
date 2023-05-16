package com.lengfj.cloud.common.core.log.aop;

import com.lengfj.cloud.common.core.log.util.LogAopParamUtil;
import com.lengfj.cloud.common.core.log.annotation.NoLog;
import com.lengfj.cloud.common.core.util.servlet.ServletUtil;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;

/**
 * 日志打印切面，默认切web下所有controller
 * @author lester
 * @date 2022/1/19 6:05 下午
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Value("${lengfj.cloud.common.log.timeout:3000}")
    public Integer timeout;

    private final static String REQUEST_MSG = "*** API REQUEST LOG [{0}]"
            + "\n[headers ] {1}"
            + "\n[request ] {2}";

    private final static String RESPONSE_MSG = "\n*** API {0} LOG [{1}]"
            + "\n[response] {2}"
            + "\n[consume ] {3}ms";

    /**
     * 切所有controller
     */
    @Pointcut("execution(* com.lengfj.cloud.*.controller.*..*(..))")
    public void cutController(){ }

    @Around("cutController()")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        return logRequest(joinPoint);
    }

    private Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        if (checkHasNoPrintAnnotation(joinPoint)){
            return joinPoint.proceed();
        }
        long start = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        String req = LogAopParamUtil.getStringFromRequest(args);
        String headers = getHeaders();
        // 获取目标对象的类名(形如：com.action.admin.LoginAction)
        String targetName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        log.info(MessageFormat.format(REQUEST_MSG, targetName, headers, req));

        String response = null;
        String responseStatus = "RESPONSE";
        try{
            Object result = joinPoint.proceed();
            response = LogAopParamUtil.getStringFromResponse(result);
            return result;
        } catch(BaseBusinessException e){
            response = e.toString();
            responseStatus = "BUSINESS EXCEPTION RESPONSE";
            throw e;
        } catch(Exception e){
            response = e.getMessage();
            responseStatus = "SYSTEM EXCEPTION RESPONSE";
            throw e;
        }finally {
            long end = System.currentTimeMillis();
            long consume = end - start;
            log.info(MessageFormat.format(RESPONSE_MSG, responseStatus, targetName, response, consume));
            if (consume > timeout) {
                log.warn("[{}]-请求执行时间过长：{}ms", targetName, consume);
            }
        }
    }

    /**
     * 校验有没有不打印日志的注解
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    private boolean checkHasNoPrintAnnotation(ProceedingJoinPoint joinPoint) {
        /*
           几种情况不打印此日志
           1. 标记了 loggerType 或 loggerMethod 注解的不打印
           2. 标记了 NoLog 的不打印
         */
        try{
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Class<?> aClass = method.getDeclaringClass();
            //类
            if (aClass.getAnnotation(NoLog.class) != null){
                return true;
            }

            //方法
            if (method.getAnnotation(NoLog.class) != null){
                return true;
            }
        }catch (Exception e){
            log.warn("打印日志获取类和方法信息时出现错误", e);
            return true;
        }
        return false;
    }

    /**
     * 获取Headers
     * @return
     */
    private String getHeaders() {
        try{
            HttpServletRequest request = ServletUtil.getRequest();
            if (request == null){
                return "";
            }
            Enumeration<String> headers = request.getHeaderNames();
            if (headers == null || !headers.hasMoreElements())
                return "[]";

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while (headers.hasMoreElements()){
                String h = headers.nextElement();
                if (sb.length() > 1)
                    sb.append(", ");
                sb.append("\"").append(h).append("\":\"");
                sb.append(request.getHeader(h)).append("\"");
            }
            sb.append("]");
            return sb.toString();
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }
}
