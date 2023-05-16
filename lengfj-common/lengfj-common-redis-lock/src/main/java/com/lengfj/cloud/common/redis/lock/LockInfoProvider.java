package com.lengfj.cloud.common.redis.lock;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 锁相关信息的提供者
 *
 * @author lester
 * @version V1.0
 * @date 2021/10/29
 **/
@Slf4j
public class LockInfoProvider {

    public static final String LOCK_KEY_TEMPLATE = "lengfj-lock:%s:%s";
    private final ExpressionParser elParser = new SpelExpressionParser();

    private final ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();


    public LockInfo getLockInfo(JoinPoint joinPoint, LenLock lockAction){
        //获取切点方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        String[] keys = lockAction.key();

        //通过el解析key 默认值=lock
        String lockKey = "lock";
        if (keys.length > 0){
            lockKey = parseEl(method, args, keys);
        }
        /*
           key组成规则 keyPrefix为空 key组成规则为：lengfj-lock:类路径.方法(xxx):key
                    keyPrefix不为空 key组成规则为：lengfj-lock:keyPrefix:key
         */
        String lockKeyName;
        if (StringUtils.isEmpty(lockAction.keyPrefix())){
            String name = signature.getDeclaringTypeName() + "." + signature.getMethod().getName();
            lockKeyName = String.format(LOCK_KEY_TEMPLATE, name, lockKey);
        }else{
            lockKeyName = String.format(LOCK_KEY_TEMPLATE, lockAction.keyPrefix(), lockKey);
        }
        return new LockInfo(
                lockAction.lockType(),
                lockKeyName,
                lockAction.waitTime(),
                lockAction.leaseTime(),
                lockAction.timeUnit(),
                lockAction.lockFailureMsg());
    }

    /**
     * 从方法参数中解析el表达式
     * @param method 方法
     * @param args 方法参数
     * @param keys 模板
     * @return 拼接好的字符串数据
     */
    private String parseEl(Method method, Object[] args, String... keys){
        //获取参数名
        String[] parameterNames = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        List<String> elKeys = new ArrayList<>();
        for (String key : keys) {
            String value = elParser.parseExpression(key).getValue(context, String.class);
            elKeys.add(value);
        }
        return CollUtil.join(elKeys, StrPool.DASHED);
    }

    public String getLockId(JoinPoint joinPoint, LenLock lockAction){
        LockInfo lockInfo = getLockInfo(joinPoint, lockAction);
        return getLockId(lockInfo.getLockName());
    }
    
    public String getLockId(String lockName){
        return Thread.currentThread().getId() + lockName;
    }




    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LockInfo{
        private LockType lockType;
        private String lockName;
        private long waitTime;
        private long leaseTime;
        private TimeUnit timeUnit;
        private String lockFailureMsg;
    }

}
