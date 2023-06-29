package com.lengfj.cloud.osme.service.processor;

import com.google.common.collect.Lists;
import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.osme.checker.Checker;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.CreateOrderDTO;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
import com.lengfj.cloud.osme.service.ProcessEventStateScenarioService;
import com.lengfj.cloud.osme.service.checker.StoreLimitOrderChecker;
import com.lengfj.cloud.osme.service.checker.UserSignChecker;
import com.lengfj.cloud.osme.checker.Checkable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付保证金事件
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 1:25 PM
 */
@Slf4j
@Service
public class PayMarginProcessor extends AbstractStateProcessor<R, CreateOrderDTO> {

    @Resource
    private UserSignChecker userSignChecker;
    @Resource
    private StoreLimitOrderChecker storeLimitOrderChecker;
    @Autowired
    private ProcessEventStateScenarioService processEventStateScenarioService;

    @Override
    public Checkable getCheckable(StateContext<CreateOrderDTO> context) {
        return new Checkable() {
            @Override
            public List<Checker> getParamChecker() {
                return Lists.newArrayList((Checker<R, CreateOrderDTO>) c -> {
                    if (c.getContext() == null){
                        return new ServiceResult<R>(500, false, R.fail("参数不能为空"));
                    }
                    return new ServiceResult<>(200,true,R.success("ok"));
                });
            }

            @Override
            public List<Checker> getAsyncChecker() {
                return Lists.newArrayList(userSignChecker, storeLimitOrderChecker);
            }
        };
    }

    @Override
    public ServiceResult<R> action(String nextState, StateContext<CreateOrderDTO> context) throws Exception {
        log.info("执行 pay 的 action 动作 订单状态变更为:" + nextState);
        return new ServiceResult<>(200,true,R.success("ok"));
    }

    @Override
    public ServiceResult<R> save(String nextState, StateContext<CreateOrderDTO> context) throws Exception {
        log.info("执行 pay 的 save 动作 订单状态变更为:" + nextState);
        return new ServiceResult<>(200,true,R.success("订单状态变更为:" + nextState));
    }
}
