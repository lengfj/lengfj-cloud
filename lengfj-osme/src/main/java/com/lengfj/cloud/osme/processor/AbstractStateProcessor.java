package com.lengfj.cloud.osme.processor;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.plugin.PluginExecutor;
import com.lengfj.cloud.osme.service.ProcessEventStateScenarioService;
import com.lengfj.cloud.osme.checker.Checkable;
import com.lengfj.cloud.osme.checker.CheckerExecutor;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.entity.ProcessEventStateScenario;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;



/**
 * 状态机处理器模板类
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:43 PM
 */
@Slf4j
public abstract class AbstractStateProcessor <T,C> implements StateProcessor<T, C>, StateActionStep<T, C> {
    @Resource
    protected CheckerExecutor checkerExecutor;
    @Resource
    protected PluginExecutor pluginExecutor;
    @Resource
    protected DataSourceTransactionManager transactionManager;
    @Resource
    protected ProcessEventStateScenarioService processEventStateScenarioService;

    /***
     * 事件执行入口
     *
     * @param context 事件状态上下文
     * @return {@link ServiceResult <T>}
     * @author lengfj
     * @date 2021/12/29 11:06 AM
     */
    @Override
    public ServiceResult<T> action(StateContext<C> context) throws Exception {
        ServiceResult<T> result = null;
        Checkable checkable = this.getCheckable(context);
        //手动创建事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //每次都创建新的事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = null;
        try {
            // 参数校验器
            result = checkerExecutor.serialCheck(checkable.getParamChecker(), context);
            if (!result.isSuccess()) {
                return result;
            }
            // 数据准备
            this.prepare(context);
            // 串行校验器
            result = checkerExecutor.serialCheck(checkable.getSyncChecker(), context);
            if (!result.isSuccess()) {
                return result;
            }
            // 并行校验器
            result = checkerExecutor.parallelCheck(checkable.getAsyncChecker(), context);
            if (!result.isSuccess()) {
                return result;
            }
            //执行事务
            transactionStatus = transactionManager.getTransaction(def);
            // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
            String nextState = this.getNextState(context);
            // 业务逻辑
            result = this.action(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            //执行插件
            result = this.pluginExecutor.parallelExecute(context);
            if (!result.isSuccess()){
                return result;
            }
            // 持久化
            result = this.save(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // 释放锁
            checkerExecutor.releaseCheck(checkable, context, result);
            // 提交事务
            transactionManager.commit(transactionStatus);
            // after
            this.after(context);
            //发送订单状态变更消息

            return result;
        } catch (Exception e) {
            if (transactionStatus != null) {
                // 回滚事务
                transactionManager.rollback(transactionStatus);
            }
            throw e;
        }
    }

    /***
     * 默认实现获取下一个状态
     *
     * @param context
     * @return {@link String}
     * @author lengfj
     * @date 2021/12/29 2:41 PM
     */
    @Override
    public String getNextState(StateContext<C> context) {
        String sceneId = context.getFsmOrder().sceneId();
        String bizCode = context.getFsmOrder().bizCode();
        String event = context.getFsmOrder().event();
        String orderState = context.getFsmOrder().getOrderState();
        //获取下一个状态
        QueryWrapper<ProcessEventStateScenario> wrapper = new QueryWrapper<>();
        wrapper.eq("process_id",bizCode);
        wrapper.eq("scenario_id",sceneId);
        wrapper.eq("state_id",orderState);
        wrapper.eq("event_id",event);
        ProcessEventStateScenario processEventStateScenario = processEventStateScenarioService.getOne(wrapper);
        if (processEventStateScenario != null){
            return String.valueOf(processEventStateScenario.getNextState());
        }
        log.warn("未获取到事件下一个状态");
        return "";
    }
}
