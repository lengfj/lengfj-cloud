package com.lengfj.cloud.osme.registry;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.lengfj.cloud.osme.dao.EventDao;
import com.lengfj.cloud.osme.dao.ProcessDao;
import com.lengfj.cloud.osme.dao.ProcessEventStateScenarioDao;
import com.lengfj.cloud.osme.entity.ProcessEventStateScenario;
import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
import com.lengfj.cloud.osme.processor.StateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 可动态编排的状态机执行器注册机
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/28 6:18 PM
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "lbb.state.processor.registry",name = "type",havingValue = "dynamic")
public class DynamicStateProcessRegistry implements ApplicationContextAware, StateProcessorRegistry {

    private static EventDao eventDao;
    private static ProcessDao processDao;
    private static ProcessEventStateScenarioDao processEventStateScenarioDao;

    private static Map<String, Map<String, Map<String, List<AbstractStateProcessor>>>> stateProcessMap1 = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("开始加载处理器------------");
         eventDao = applicationContext.getBean(EventDao.class);
         processDao = applicationContext.getBean(ProcessDao.class);
         processEventStateScenarioDao = applicationContext.getBean(ProcessEventStateScenarioDao.class);

        Wrapper<ProcessEventStateScenario> stateWrapper = new EntityWrapper<>();
        List<ProcessEventStateScenario> processEventStateScenarios = processEventStateScenarioDao.selectList(stateWrapper);

        if(!CollectionUtils.isEmpty(processEventStateScenarios)){
            processEventStateScenarios.forEach(p->{
                this.registerStateHandlers(p.getStateId().toString(),p.getEventId().toString(),p.getProcessId().toString(),p.getScenarioId().toString(),stateProcessMap1,applicationContext.getBean(p.getProcessorName(),AbstractStateProcessor.class));
            });
        }

        log.info("处理器加载结束 ε＝ε＝ε＝(#>д<)ﾉ 加载了{}个处理器",processEventStateScenarios.size());
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
    public List<AbstractStateProcessor> acquireStateProcess(String orderState, String eventType, String bizCode, String sceneId) {
        Map<String, Map<String, List<AbstractStateProcessor>>> stateMap = stateProcessMap1.get(orderState);
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
