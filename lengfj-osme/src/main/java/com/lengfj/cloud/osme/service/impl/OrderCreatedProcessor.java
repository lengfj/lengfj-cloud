package com.lengfj.cloud.osme.service.impl;//package com.lengfj.cloud.osme.service.impl;
//
//import com.google.common.collect.Lists;
//import com.lengfj.cloud.osme.checker.Checkable;
//import com.lengfj.cloud.osme.checker.Checker;
//import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
//import com.lengfj.cloud.osme.processor.OrderProcessor;
//import com.lengfj.cloud.osme.module.CreateOrderDTO;
//import com.lengfj.cloud.osme.module.ServiceResult;
//import com.lengfj.cloud.osme.engine.StateContext;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * todo
// *
// * @author lengfj
// * @version 1.0.0
// * @since 2021/12/23 6:29 PM
// */
//@Service
////@OrderProcessor(state = "init", event = "create", bizCode = "business")
//public class OrderCreatedProcessor extends AbstractStateProcessor<String, CreateOrderDTO> {
//
//    @Resource
//    private CreateParamChecker createParamChecker;
//
//    @Override
//    public boolean filter(StateContext<CreateOrderDTO> context) {
//        return true;
//    }
//
//    @Override
//    public Checkable getCheckable(StateContext<CreateOrderDTO> context) {
//        return new Checkable() {
//            @Override
//            public List<Checker> getParamChecker() {
//                return Lists.newArrayList(createParamChecker);
//            }
//
//        };
//    }
//
//    @Override
//    public String getNextState(StateContext<CreateOrderDTO> context) {
//        return "CREATE_PAY";
//    }
//
//    @Override
//    public ServiceResult<String> action(String nextState, StateContext<CreateOrderDTO> context) throws Exception {
//        return null;
//    }
//
//    @Override
//    public ServiceResult<String> save(String nextState, StateContext<CreateOrderDTO> context) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void after(StateContext<CreateOrderDTO> context) {
//
//    }
//}
