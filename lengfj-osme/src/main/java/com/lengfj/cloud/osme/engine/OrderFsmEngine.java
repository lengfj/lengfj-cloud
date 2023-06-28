package com.lengfj.cloud.osme.engine;


import com.lengfj.cloud.osme.module.ServiceResult;

/**
 * 状态机执行引擎
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 4:29 PM
 */
public interface OrderFsmEngine {

    /**
     * 执行状态迁移事件，不传FsmOrder默认会根据orderId从FsmOrderService接口获取, context内容为空
     */
    <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent) throws Exception;

    /**
     * 执行状态迁移事件，不传FsmOrder默认会根据orderId从FsmOrderService接口获取
     */
    <T, C> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, C context) throws Exception;
    /**
     * 执行状态迁移事件，可携带FsmOrder参数
     */
    <T, C> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder, C context) throws Exception;
}
