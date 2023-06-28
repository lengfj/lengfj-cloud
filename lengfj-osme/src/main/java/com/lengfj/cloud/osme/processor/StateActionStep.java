package com.lengfj.cloud.osme.processor;


import com.lengfj.cloud.osme.checker.Checkable;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;

/**
 * 状态迁移动作处理步骤
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:40 PM
 */
public interface StateActionStep<T, C> {

    /**
     * 当无法有效识别定位唯一processor时，判断是否符合调用条件，
     * @param context context
     * @return true 符合调用条件，false 不符合调用条件
     */
    default boolean filter(StateContext<C> context){
        return true;
    }

    /**
     * 准备数据
     */
    default void prepare(StateContext<C> context) {
    }
    /**
     * 校验
     */
    Checkable getCheckable(StateContext<C> context);
    /**
     * 获取当前状态处理器处理完毕后，所处于的下一个状态
     */
    String getNextState(StateContext<C> context);
    /**
     * 状态动作方法，主要状态迁移逻辑
     */
    ServiceResult<T> action(String nextState, StateContext<C> context) throws Exception;
    /**
     * 状态数据持久化
     */
    ServiceResult<T> save(String nextState, StateContext<C> context) throws Exception;
    /**
     * 状态迁移成功，持久化后执行的后续处理
     */
    default void after(StateContext<C> context) {
    }
}
