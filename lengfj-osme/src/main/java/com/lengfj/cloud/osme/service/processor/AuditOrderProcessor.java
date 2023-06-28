package com.lengfj.cloud.osme.service.processor;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.osme.checker.Checkable;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
import org.springframework.stereotype.Service;

/**
 * 审核订单事件
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 1:32 PM
 */
@Service
public class AuditOrderProcessor extends AbstractStateProcessor<R, CreateOrderProcessor> {


    @Override
    public Checkable getCheckable(StateContext<CreateOrderProcessor> context) {
        return null;
    }

    @Override
    public ServiceResult<R> action(String nextState, StateContext<CreateOrderProcessor> context) throws Exception {
        return null;
    }

    @Override
    public ServiceResult<R> save(String nextState, StateContext<CreateOrderProcessor> context) throws Exception {
        return null;
    }
}
