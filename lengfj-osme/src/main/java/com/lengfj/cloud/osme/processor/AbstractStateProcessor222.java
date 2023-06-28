package com.lengfj.cloud.osme.processor;//package com.lester.order.statemachine.engine;
//
//import com.lester.order.statemachine.checker.Checkable;
//import com.lester.order.statemachine.checker.CheckerExecutor;
//import com.lester.order.statemachine.module.ServiceResult;
//import com.lester.order.statemachine.module.StateContext;
//import com.lester.order.statemachine.plugin.PluginExecutor;
//import org.apache.rocketmq.client.producer.LocalTransactionState;
//import org.apache.rocketmq.client.producer.TransactionListener;
//import org.apache.rocketmq.client.producer.TransactionMQProducer;
//import org.apache.rocketmq.client.producer.TransactionSendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * 状态机处理器模板类
// *
// * @author lengfj
// * @version 1.0.0
// * @since 2021/12/23 3:43 PM
// */
//@Component
//public abstract class AbstractStateProcessor222<T,C> implements StateProcessor<T, C>, StateActionStep<T, C> {
//    @Resource
//    private CheckerExecutor checkerExecutor;
//    @Resource
//    private PluginExecutor pluginExecutor;
//    @Resource
//    private RocketMQTemplate rocketMQTemplate;
//    @Resource
//    private TransactionMQProducer transactionMQProducer;
//
//    @Override
//    public final ServiceResult<T> action(StateContext<C> context) throws Exception {
//        ServiceResult<T> result = null;
//        Checkable checkable = this.getCheckable(context);
//        try {
//            // 参数校验器
//            result = checkerExecutor.serialCheck(checkable.getParamChecker(), context);
//            if (!result.isSuccess()) {
//                return result;
//            }
//            // 数据准备
//            this.prepare(context);
//            // 串行校验器
//            result = checkerExecutor.serialCheck(checkable.getSyncChecker(), context);
//            if (!result.isSuccess()) {
//                return result;
//            }
//            // 并行校验器
//            result = checkerExecutor.parallelCheck(checkable.getAsyncChecker(), context);
//            if (!result.isSuccess()) {
//                return result;
//            }
//            // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
//            String nextState = this.getNextState(context);
//            transactionMQProducer.setTransactionListener(new TransactionListener() {
//                @Override
//                public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//                    HashMap<String,Object> arg1 = (HashMap<String,Object>) arg;
//                    String nextState1 = (String)arg1.get("nextState");
//                    try {
//                        // 业务逻辑
//                        ServiceResult<T> actionResult = action(nextState1, context);
//                        if (!actionResult.isSuccess()) {
//                            return LocalTransactionState.ROLLBACK_MESSAGE;
//                        }
//                        //执行插件
//                        pluginExecutor.parallelExecute(context);
//                        // 持久化
//                        ServiceResult<T> saveResult = save(nextState, context);
//                        if (!saveResult.isSuccess()) {
//                            return LocalTransactionState.ROLLBACK_MESSAGE;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return LocalTransactionState.COMMIT_MESSAGE;
//                }
//
//                @Override
//                public LocalTransactionState checkLocalTransaction(MessageExt msg) {
//                    return null;
//                }
//            });
//            Message message = new Message();
//            message.setTopic("TOPIC");
//            message.setTags("tags");
//            Map<String, Object> map = new HashMap<>();
//            map.put("nextState", nextState);
//            map.put("context", context);
//            TransactionSendResult transactionSendResult = transactionMQProducer.sendMessageInTransaction(message, map);
//
//
//            // 释放锁
//            checkerExecutor.releaseCheck(checkable, context, result);
//            // after
//            this.after(context);
//            //发送订单状态变更消息
//
//            return result;
//        } catch (Exception e) {
//            throw e;
//        }
//    }
//}
