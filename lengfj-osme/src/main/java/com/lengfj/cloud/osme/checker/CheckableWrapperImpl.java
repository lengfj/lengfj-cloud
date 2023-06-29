package com.lengfj.cloud.osme.checker;

import com.google.common.collect.Lists;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.engine.StateContext;

import java.util.List;
import java.util.function.Function;

/**
 * @author lester
 * @date 2022/1/28 2:41 下午
 */
public class CheckableWrapperImpl<C> implements CheckableWrapper<C>{
    @Override
    public Checkable simpleCheck(Function<StateContext<C>, ServiceResult> function) {

        return new Checkable() {
            @Override
            public List<Checker> getParamChecker() {
                return Lists.newArrayList(function::apply);
            }
        };
    }
}
