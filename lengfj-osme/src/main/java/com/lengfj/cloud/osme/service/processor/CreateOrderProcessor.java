package com.lengfj.cloud.osme.service.processor;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.common.id.ID;
import com.lengfj.cloud.osme.checker.Checkable;
import com.lengfj.cloud.osme.checker.CheckableWrapper;
import com.lengfj.cloud.osme.checker.CheckableWrapperImpl;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.entity.ProcessEventStateScenario;
import com.lengfj.cloud.osme.module.CreateOrderDTO;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
import com.lengfj.cloud.osme.service.checker.StoreLimitOrderChecker;
import com.lengfj.cloud.osme.service.checker.UserSignChecker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 下单事件
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 1:25 PM
 */
@Slf4j
@Service
public class CreateOrderProcessor extends AbstractStateProcessor<R, CreateOrderDTO> {

    @Resource
    private UserSignChecker userSignChecker;
    @Resource
    private StoreLimitOrderChecker storeLimitOrderChecker;

//    @Override
//    public Checkable getCheckable(StateContext<CreateOrderDTO> context) {
//        return new Checkable() {
//            @Override
//            public List<Checker> getParamChecker() {
//                return Lists.newArrayList((Checker<R, CreateOrderDTO>) c -> {
//                    if (c.getContext() == null){
//                        return new ServiceResult<R>(500, false, R.fail("参数不能为空"));
//                    }
//                    return new ServiceResult(200,true,"ok");
//                });
//            }
//
//            @Override
//            public List<Checker> getAsyncChecker() {
//                return Lists.newArrayList(userSignChecker, storeLimitOrderChecker);
//            }
//        };
//    }

    @Override
    public Checkable getCheckable(StateContext<CreateOrderDTO> context) {
        CheckableWrapper<CreateOrderDTO> checkableWrapper = new CheckableWrapperImpl<>();
        return checkableWrapper.simpleCheck((conte) -> {
            log.info(conte.toString());
            return new ServiceResult<>(200,true,R.success("ok"));
        });
    }

    @Override
    public ServiceResult<R> action(String nextState, StateContext<CreateOrderDTO> context) throws Exception {
        log.info("Create 执行action动作");
        ProcessEventStateScenario processEventStateScenario = processEventStateScenarioService.getById(1);
        log.info(processEventStateScenario.toString());
        return new ServiceResult<>(200,true,R.success("ok"));
    }

    @Override
    public ServiceResult<R> save(String nextState, StateContext<CreateOrderDTO> context) throws Exception {
        log.info("Create 执行save动作  订单状态变更为：{}", nextState);
        String snowflakeId = ID.getSnowflakeId();
        String segmentId = ID.getSegmentId("test");
        log.info("号段模式生成id[{}], 雪花模式生成id:[{}]", segmentId, snowflakeId);

        String custom = custom();
        return new ServiceResult<>(200,true,R.success("订单状态变更为：" + nextState + "， 扩展：" + custom));
    }


    protected String custom(){
        log.info("默认的扩展方法执行---");
        return "create";
    }
}
