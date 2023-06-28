package com.lengfj.cloud.osme.service.checker;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.osme.exception.OsmeException;
import com.lengfj.cloud.osme.checker.Checker;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.CreateOrderDTO;
import com.lengfj.cloud.osme.module.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户签约校验器 入参：用户id
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 1:49 PM
 */
@Slf4j
@Component
public class UserSignChecker implements Checker<R, CreateOrderDTO> {
    @Override
    public ServiceResult<R> check(StateContext<CreateOrderDTO> context) {
        log.info("用户签约校验器 入参：" + context);
        if (context.getContext().getId().equals(1L)){
            throw new OsmeException("200","用户校验失败");
        }
//        Long userId = context.getContext();
        return new ServiceResult<>(200,true, R.success("已签约"));
    }
}
