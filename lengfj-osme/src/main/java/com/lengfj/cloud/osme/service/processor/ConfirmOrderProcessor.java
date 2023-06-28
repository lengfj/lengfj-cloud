package com.lengfj.cloud.osme.service.processor;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.osme.checker.Checkable;
import com.lengfj.cloud.osme.checker.Checker;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 确认订单事件
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 1:32 PM
 */
@Slf4j
@Service
public class ConfirmOrderProcessor extends AbstractStateProcessor<R, CreateOrderProcessor> {

    @Override
    public boolean filter(StateContext<CreateOrderProcessor> context) {
        if (context.getFsmOrder().bizCode().equals("1")){
            return true;
        }
        return false;
    }

    @Override
    public Checkable getCheckable(StateContext<CreateOrderProcessor> context) {
        return new Checkable() {
            @Override
            public List<Checker> getParamChecker() {
                return null;
            }
        };
    }

    @Override
    public ServiceResult<R> action(String nextState, StateContext<CreateOrderProcessor> context) throws Exception {
        log.info("执行 confirm 的 action 动作 订单状态变更为:" + nextState);
        return new ServiceResult<>(200,true,R.success("ok"));
    }

    @Override
    public ServiceResult<R> save(String nextState, StateContext<CreateOrderProcessor> context) throws Exception {
        log.info("执行 confirm 的 save 动作 订单状态变更为:" + nextState);
        return new ServiceResult<>(200,true,R.success("订单状态变更为:" + nextState));
    }
}
