package com.lengfj.cloud.osme.checker;

import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.ServiceResult;

import java.util.function.Function;

/**
 * 校验器包装类
 * @author lester
 * @date 2022/1/28 2:29 下午
 */
public interface CheckableWrapper<C> {

    Checkable simpleCheck(Function<StateContext<C>, ServiceResult> function);


}
