package com.lengfj.cloud.osme.registry;

import com.lengfj.cloud.osme.processor.AbstractStateProcessor;

import java.util.List;

/**
 * 状态事件处理器注册
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 5:09 PM
 */
public interface StateProcessorRegistry {

    /**
     * 从状态机处理器列表
     * @param orderState
     * @param eventType
     * @param bizCode
     * @param sceneId
     * @return
     */
    List<AbstractStateProcessor> acquireStateProcess(String orderState, String eventType, String bizCode, String sceneId);
}
