package com.lengfj.cloud.osme.processor;


import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;

/**
 * 状态机处理器接口
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:47 PM
 */
public interface StateProcessor<T, C> {

    /**
     * 执行状态迁移的入口
     */
    ServiceResult<T> action(StateContext<C> context) throws Exception;
}
