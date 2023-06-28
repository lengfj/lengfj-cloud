package com.lengfj.cloud.osme.plugin;

import com.lengfj.cloud.osme.engine.FsmOrder;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 插件执行器
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/27 10:03 AM
 */
@Slf4j
@Component
public class PluginExecutor {

    private final static ExecutorService EXECUTOR = new ThreadPoolExecutor(
            10,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(1000));

    @Resource
    private PluginProcessorRegistry pluginProcessorRegistry;

    /**
     * 并行插件执行
     * @param context
     * @param <T>
     * @param <C>
     * @return
     */
    public <T,C> ServiceResult<T> parallelExecute(StateContext<C> context){
        String eventType = context.getOrderStateEvent().getEventType();
        FsmOrder fsmOrder = context.getFsmOrder();
        String orderState = fsmOrder.getOrderState();
        String bizCode = fsmOrder.bizCode();
        String sceneId = fsmOrder.sceneId();
        List<PluginHandler> pluginHandlers = pluginProcessorRegistry.acquirePluginProcess(orderState, eventType, bizCode, sceneId);
        if (CollectionUtils.isEmpty(pluginHandlers)){
            return new ServiceResult<>();
        }
        List<Future<ServiceResult>> futureResultList = Collections.synchronizedList(new ArrayList<>(pluginHandlers.size()));
        for (PluginHandler pluginHandler : pluginHandlers){
            Future<ServiceResult> future = EXECUTOR.submit(() -> pluginHandler.action(context));
            futureResultList.add(future);
        }
        for (Future<ServiceResult> f : futureResultList){
            try {
                ServiceResult sr = f.get();
                if (!sr.isSuccess()) {
                    return sr;
                }
            } catch (Exception e) {
                log.error("parallelPlugin executor.submit error.", e);
                throw new RuntimeException(e);
            }
        }
        return new ServiceResult<>();
    }


}
