package com.lengfj.cloud.osme.plugin;

import java.util.List;

/**
 * 插件处理器注册机
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/27 9:43 AM
 */
public interface PluginProcessorRegistry {

    /***
     * 获取插件处理器
     *
     * @param orderState
     * @param eventType
     * @param bizCode
     * @param sceneId
     * @return {@link List< PluginHandler >}
     * @author lengfj
     * @date 2021/12/27 9:44 AM
     */
    List<PluginHandler> acquirePluginProcess(String orderState, String eventType, String bizCode, String sceneId);
}
