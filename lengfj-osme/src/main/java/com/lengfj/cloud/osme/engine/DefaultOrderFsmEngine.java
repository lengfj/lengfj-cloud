package com.lengfj.cloud.osme.engine;

import com.lengfj.cloud.osme.exception.FsmException;
import com.lengfj.cloud.osme.exception.OsmeExceptionEnum;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.processor.AbstractStateProcessor;
import com.lengfj.cloud.osme.processor.StateProcessor;
import com.lengfj.cloud.osme.registry.StateProcessorRegistry;
import com.lengfj.cloud.osme.service.IFsmOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 4:31 PM
 */
@Component
public class DefaultOrderFsmEngine implements OrderFsmEngine{

    @Resource
    private IFsmOrderService fsmOrderService;
    @Resource
    private StateProcessorRegistry stateProcessorRegistry;

    @Override
    public <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent) throws Exception {
        FsmOrder fsmOrder = null;
        if (orderStateEvent.newCreate()) {
            fsmOrder = this.fsmOrderService.getFsmOrder(orderStateEvent.getOrderId());
            if (fsmOrder == null) {
                throw new FsmException(OsmeExceptionEnum.ORDER_NOT_FOUND);
            }
        }
        return sendEvent(orderStateEvent, fsmOrder, null);
    }

    @Override
    public <T, C> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, C context) throws Exception {
        FsmOrder fsmOrder = null;
        if (orderStateEvent.newCreate()) {
            fsmOrder = this.fsmOrderService.getFsmOrder(orderStateEvent.getOrderId());
            if (fsmOrder == null) {
                throw new FsmException(OsmeExceptionEnum.ORDER_NOT_FOUND);
            }
        }
        return sendEvent(orderStateEvent, fsmOrder, context);
    }
    @Override
    public <T, C> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder, C context) throws Exception {
        // 构造当前事件上下文
        StateContext stateContext = this.getStateContext(orderStateEvent, fsmOrder, context);
        // 获取当前事件处理器
        StateProcessor stateProcessor = this.getStateProcessor(stateContext);
        // 执行处理逻辑
        return stateProcessor.action(stateContext);
    }
    private <T> StateProcessor<T, ?> getStateProcessor(StateContext<?> context) {
        OrderStateEvent stateEvent = context.getOrderStateEvent();
        FsmOrder fsmOrder = context.getFsmOrder();
        // 根据状态+事件对象获取所对应的业务处理器集合
        List<AbstractStateProcessor> processorList = stateProcessorRegistry.acquireStateProcess(fsmOrder.getOrderState(),
                stateEvent.getEventType(), fsmOrder.bizCode(), fsmOrder.sceneId());
        if (processorList == null) {
            // 订单状态发生改变
            if (!Objects.isNull(stateEvent.orderState()) && !stateEvent.orderState().equals(fsmOrder.getOrderState())) {
                throw new FsmException(OsmeExceptionEnum.ORDER_STATE_NOT_MATCH);
            }
            throw new FsmException(OsmeExceptionEnum.NOT_FOUND_PROCESSOR);
        }
        /*
         * 通过状态 + 事件 + bieCode@sceneId 获取到的业务处理器是否满足前置条件；并且满足条件的处理器不能有多个
         */
        List<AbstractStateProcessor> processorResult = new ArrayList<>(processorList.size());
        // 根据上下文获取唯一的业务处理器
        for (AbstractStateProcessor processor : processorList) {
            if (processor.filter(context)) {
                processorResult.add(processor);
            }
        }
        if (CollectionUtils.isEmpty(processorResult)) {
            throw new FsmException(OsmeExceptionEnum.NOT_FOUND_PROCESSOR);
        }
        if (processorResult.size() > 1) {
            throw new FsmException(OsmeExceptionEnum.FOUND_MORE_PROCESSOR);
        }
        return processorResult.get(0);
    }

    /**
     * 构造事件上下文
     */
    private <C> StateContext<?>  getStateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder, C context) {
        StateContext<?> stateContext = new StateContext(orderStateEvent, fsmOrder, context);
        return stateContext;
    }
}
