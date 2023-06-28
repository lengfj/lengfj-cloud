package com.lengfj.cloud.osme.service.impl;

import com.lengfj.cloud.osme.checker.Checker;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;
import org.springframework.stereotype.Component;

/**
 * 创建订单参数校验器
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 6:54 PM
 */
@Component
public class CreateParamChecker<Object,CreateOrderContext> implements Checker<Object,CreateOrderContext> {
    @Override
    public ServiceResult<Object> check(StateContext<CreateOrderContext> context) {
        if (context.getContext() == null){
            return new ServiceResult(500,false,"失败");
        }
        return new ServiceResult(200,true, "校验成功");
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public boolean needRelease() {
        return true;
    }

    @Override
    public void release(StateContext<CreateOrderContext> context, ServiceResult<Object> result) {
        //释放锁
    }
}
