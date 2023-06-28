package com.lengfj.cloud.osme.engine;

import lombok.Data;

/**
 * 状态上下文
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:28 PM
 */
@Data
public class StateContext<C> {

    /**
     * 订单状态迁移事件
     */
    private OrderStateEvent orderStateEvent;

    /**
     * 状态机需要的订单基本信息
     */
    private FsmOrder fsmOrder;

    /**
     * 业务可定义的上下文泛型对象
     */
    private C context;

    public StateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
        this.orderStateEvent = orderStateEvent;
        this.fsmOrder = fsmOrder;
    }

    public StateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder, C context) {
        this.orderStateEvent = orderStateEvent;
        this.fsmOrder = fsmOrder;
        this.context = context;
    }
}
