package com.lengfj.cloud.osme.service.checker;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.osme.checker.Checker;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.CreateOrderDTO;
import com.lengfj.cloud.osme.module.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 店铺限制下单校验器 入参：店铺id
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 1:53 PM
 */
@Slf4j
@Component
public class StoreLimitOrderChecker implements Checker<R, CreateOrderDTO> {
    @Override
    public ServiceResult<R> check(StateContext<CreateOrderDTO> context) {
        log.info("店铺限制下单校验器 入参：" + context);
        return new ServiceResult<>(200,true,R.success("可以下单"));
    }
}
