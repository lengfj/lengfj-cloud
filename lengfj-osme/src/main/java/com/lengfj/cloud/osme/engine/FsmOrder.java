package com.lengfj.cloud.osme.engine;

/**
 * 状态机引擎所需的订单信息基类信息
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:30 PM
 */
public interface FsmOrder {

    /**
     * 订单ID
     */
    String getOrderId();
    /**
     * 订单状态
     */
    String getOrderState();
    /**
     * 订单的业务属性
     */
    String bizCode();
    /**
     * 订单的场景属性
     */
    String sceneId();

    /**
     * 事件
     */
    String event();

}
