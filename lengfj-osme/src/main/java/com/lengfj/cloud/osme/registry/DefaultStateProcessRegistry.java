package com.lengfj.cloud.osme.registry;

import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
import com.lengfj.cloud.osme.processor.OrderProcessor;
import com.lengfj.cloud.osme.processor.StateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认状态事件处理器注册
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 4:26 PM
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "lbb.state.processor.registry",name = "type", havingValue = "default", matchIfMissing = true)
public class DefaultStateProcessRegistry implements BeanPostProcessor, StateProcessorRegistry {

    /**
     * 第一层key是订单状态。
     * 第二层key是订单状态对应的事件，一个状态可以有多个事件。
     * 第三层key是具体场景code，场景下对应的多个处理器，需要后续进行过滤选择出一个具体的执行。
     */
    private static Map<String, Map<String, Map<String, List<AbstractStateProcessor>>>> stateProcessMap = new ConcurrentHashMap<>();
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("DefaultStateProcessRegistry ε＝ε＝ε＝(#>д<)ﾉ");
        if (bean instanceof AbstractStateProcessor && bean.getClass().isAnnotationPresent(OrderProcessor.class)) {
            OrderProcessor annotation = bean.getClass().getAnnotation(OrderProcessor.class);
            String[] states = annotation.state();
            String event = annotation.event();
            String[] bizCodes = annotation.bizCode().length == 0 ? new String[]{"#"} : annotation.bizCode();
            String[] sceneIds = annotation.sceneId().length == 0 ? new String[]{"#"} : annotation.sceneId();
            initProcessMap(states, event, bizCodes, sceneIds, stateProcessMap, (AbstractStateProcessor) bean);
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

    /***
     * 获取注册机中的事件处理器
     *
     * @param orderState
     * @param eventType
     * @param bizCode
     * @param sceneId
     * @return {@link List< AbstractStateProcessor >}
     * @author lengfj
     * @date 2021/12/24 10:46 AM
     */
    @Override
    public List<AbstractStateProcessor> acquireStateProcess(String orderState, String eventType, String bizCode, String sceneId) {
        Map<String, Map<String, List<AbstractStateProcessor>>> stateMap = stateProcessMap.get(orderState);
        if (CollectionUtils.isEmpty(stateMap)){
            return new ArrayList<>();
        }
        Map<String, List<AbstractStateProcessor>> eventMap = stateMap.get(eventType);
        if (CollectionUtils.isEmpty(eventMap)){
            return new ArrayList<>();
        }
        String bizCodeAndSceneId = bizCode + "@" + sceneId;
        return eventMap.get(bizCodeAndSceneId);
    }
}
