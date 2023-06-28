package com.lengfj.cloud.osme.module;

import com.lengfj.cloud.osme.engine.OrderStateEvent;
import lombok.ToString;

/**
 * 默认订单状态迁移事件字段
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 3:26 PM
 */
@ToString
public class DefaultOrderStateEvent implements OrderStateEvent {

    private String eventType;

    private String orderId;

    private String orderState;

    private boolean newCreate;

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public void setNewCreate(boolean newCreate) {
        this.newCreate = newCreate;
    }

    @Override
    public String getEventType() {
        return this.eventType;
    }

    @Override
    public String getOrderId() {
        return this.orderId;
    }

    @Override
    public String orderState() {
        return orderState;
    }

    @Override
    public boolean newCreate() {
        return newCreate;
    }
}
