package com.lengfj.cloud.osme.plugin;

import com.lengfj.cloud.osme.processor.StateProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认插件处理器的注册机
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/27 9:48 AM
 */
@Component
public class DefaultPluginProcessorRegistry implements BeanPostProcessor, PluginProcessorRegistry {

    /**
     * 第一层key是订单状态。
     * 第二层key是订单状态对应的事件，一个状态可以有多个事件。
     * 第三层key是具体场景code，场景下对应的多个处理器，需要后续进行过滤选择出一个具体的执行。
     */
    private static Map<String, Map<String, Map<String, List<PluginHandler>>>> pluginProcessMap = new ConcurrentHashMap<>();
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof PluginHandler && bean.getClass().isAnnotationPresent(ProcessorPlugin.class)) {
            ProcessorPlugin annotation = bean.getClass().getAnnotation(ProcessorPlugin.class);
            String[] states = annotation.state();
            String event = annotation.event();
            String[] bizCodes = annotation.bizCode().length == 0 ? new String[]{"#"} : annotation.bizCode();
            String[] sceneIds = annotation.sceneId().length == 0 ? new String[]{"#"} : annotation.sceneId();
            initProcessMap(states, event, bizCodes, sceneIds, pluginProcessMap, (PluginHandler) bean);
        }
        return bean;
    }
    private <E extends StateProcessor> void initProcessMap(String[] states, String event, String[] bizCodes, String[] sceneIds,
                                                           Map<String, Map<String, Map<String, List<E>>>> map, E processor) {
        for (String bizCode : bizCodes) {
            for (String sceneId : sceneIds) {
                Arrays.asList(states).parallelStream().forEach(orderStateEnum -> {
                    registerStateHandlers(orderStateEnum, event, bizCode, sceneId, map, processor);
                });
            }
        }
    }
    /**
     * 初始化状态机处理器
     */
    public <E extends StateProcessor> void registerStateHandlers(String orderStateEnum, String event, String bizCode, String sceneId,
                                                                 Map<String, Map<String, Map<String, List<E>>>> map, E processor) {
        // state维度
        if (!map.containsKey(orderStateEnum)) {
            map.put(orderStateEnum, new ConcurrentHashMap<>());
        }
        Map<String, Map<String, List<E>>> stateTransformEventEnumMap = map.get(orderStateEnum);
        // event维度
        if (!stateTransformEventEnumMap.containsKey(event)) {
            stateTransformEventEnumMap.put(event, new ConcurrentHashMap<>());
        }
        // bizCode and sceneId
        Map<String, List<E>> processorMap = stateTransformEventEnumMap.get(event);
        String bizCodeAndSceneId = bizCode + "@" + sceneId;
        if (!processorMap.containsKey(bizCodeAndSceneId)) {
            processorMap.put(bizCodeAndSceneId, new CopyOnWriteArrayList<>());
        }
        processorMap.get(bizCodeAndSceneId).add(processor);
    }

    @Override
    public List<PluginHandler> acquirePluginProcess(String orderState, String eventType, String bizCode, String sceneId) {
        Map<String, Map<String, List<PluginHandler>>> stateMap = pluginProcessMap.get(orderState);
        if (CollectionUtils.isEmpty(stateMap)){
            return new ArrayList<>();
        }
        Map<String, List<PluginHandler>> eventMap = stateMap.get(eventType);
        if (CollectionUtils.isEmpty(eventMap)){
            return new ArrayList<>();
        }
        String bizCodeAndSceneId = bizCode + "@" + sceneId;
        return eventMap.get(bizCodeAndSceneId);
    }
}
