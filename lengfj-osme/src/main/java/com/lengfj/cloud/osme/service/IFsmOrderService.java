package com.lengfj.cloud.osme.service;


import com.lengfj.cloud.osme.engine.FsmOrder;

/**
 *
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 4:51 PM
 */
public interface IFsmOrderService {

    /**
     * 获取fsmOrder对象
     *
     * @param orderId
     * @return {@link FsmOrder}
     * @author lengfj
     * @date 2021/12/23 4:52 PM
     */
    FsmOrder getFsmOrder(String orderId);
}
