package com.lengfj.cloud.osme.checker;


import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;

/**
 * 状态机校验器
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:52 PM
 */
public interface Checker<T, C> {



    ServiceResult<T> check(StateContext<C> context);
    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }

    /**
     * 是否需求release,可以在这里加锁
     */
    default boolean needRelease() {
        return false;
    }
    /**
     * 业务执行完成后的释放方法,
     * 比如有些业务会在checker中加一些状态操作，等业务执行完成后根据结果选择处理这些状态操作,
     * 最典型的就是checker中加一把锁，release根据结果释放锁.
     */
    default void release(StateContext<C> context, ServiceResult<T> result) {
    }
}
