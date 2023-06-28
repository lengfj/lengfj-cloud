package com.lengfj.cloud.osme.checker;

import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 校验器执行
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:54 PM
 */
@Slf4j
@Component
public class CheckerExecutor{

    private final static ExecutorService EXECUTOR = new ThreadPoolExecutor(
            10,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(1000));


    /**
     * 执行并行校验器，
     * 按照任务投递的顺序判断返回。
     */
    public <T,C> ServiceResult<T> parallelCheck(List<Checker> checkers, StateContext<C> context) {
        if (!CollectionUtils.isEmpty(checkers)) {
            if (checkers.size() == 1) {
                return checkers.get(0).check(context);
            }
            List<Future<ServiceResult>> resultList = Collections.synchronizedList(new ArrayList<>(checkers.size()));
            checkers.sort(Comparator.comparingInt(Checker::order));
            for (Checker c : checkers) {
                Future<ServiceResult> future = EXECUTOR.submit(() -> c.check(context));
                resultList.add(future);
            }
            for (Future<ServiceResult> future : resultList) {
                try {
                    ServiceResult sr = future.get();
                    if (!sr.isSuccess()) {
                        return sr;
                    }
                }catch (BaseBusinessException e) {
                    throw e;
                }catch (Exception e) {
                    if (e.getCause() instanceof BaseBusinessException){
                        throw (BaseBusinessException)e.getCause();
                    }
                    log.error("parallelCheck executor.submit error.", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return new ServiceResult(200,true,"success");
    }

    /**
     * 执行串行校验器
     * 按照任务投递的顺序判断返回。
     * @param checkers
     * @param context
     * @param <T>
     * @param <C>
     * @return
     */
    public <T,C> ServiceResult<T> serialCheck(List<Checker> checkers, StateContext<C> context) {
        if (!CollectionUtils.isEmpty(checkers)){
            if (checkers.size() == 1){
                return checkers.get(0).check(context);
            }
            checkers.sort(Comparator.comparingInt(Checker::order));
            for (Checker c : checkers) {
                ServiceResult result = c.check(context);
                if (!result.isSuccess()){
                    return result;
                }
            }
        }
        return new ServiceResult(200,true,"success");
    }


    /**
     * 执行checker的释放操作
     */
    public <T, C> void releaseCheck(Checkable checkable, StateContext<C> context, ServiceResult<T> result) {
        List<Checker> checkers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(checkable.getParamChecker())){
            checkers.addAll(checkable.getParamChecker());
        }
        if (!CollectionUtils.isEmpty(checkable.getSyncChecker())){
            checkers.addAll(checkable.getSyncChecker());
        }
        if (!CollectionUtils.isEmpty(checkable.getAsyncChecker())){
            checkers.addAll(checkable.getAsyncChecker());
        }
        checkers.removeIf(Checker::needRelease);
        if (!CollectionUtils.isEmpty(checkers)) {
            if (checkers.size() == 1) {
                checkers.get(0).release(context, result);
                return;
            }
            CountDownLatch latch = new CountDownLatch(checkers.size());
            for (Checker c : checkers) {
                EXECUTOR.execute(() -> {
                    try {
                        c.release(context, result);
                    } finally {
                        latch.countDown();
                    }
                });
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
