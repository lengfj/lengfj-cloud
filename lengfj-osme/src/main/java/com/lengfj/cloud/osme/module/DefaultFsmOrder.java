package com.lengfj.cloud.osme.module;

import com.lengfj.cloud.osme.engine.FsmOrder;
import lombok.ToString;

/**
 * todo
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 1:39 PM
 */
@ToString
public class DefaultFsmOrder implements FsmOrder {

    private String orderId;

    private String orderState;

    private String bizCode;

    private String sceneId;

    private String event;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String getOrderId() {
        return this.orderId;
    }

    @Override
    public String getOrderState() {
        return this.orderState;
    }

    @Override
    public String bizCode() {
        return this.bizCode;
    }

    @Override
    public String sceneId() {
        return this.sceneId;
    }

    @Override
    public String event() {
        return this.event;
    }


}
