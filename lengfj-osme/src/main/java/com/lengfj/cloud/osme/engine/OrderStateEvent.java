package com.lengfj.cloud.osme.engine;

/**
 * 订单状态迁移事件
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:32 PM
 */
public interface OrderStateEvent {

    /**
     * 订单状态事件
     */
    String getEventType();
    /**
     * 订单ID
     */
    String getOrderId();
    /**
     * 如果orderState不为空，则代表只有订单是当前状态才进行迁移
     */
    default String orderState() {
        return null;
    }
    /**
     * 是否要新创建订单
     */
    boolean newCreate();

}
