package com.lengfj.cloud.osme.module;

import lombok.Data;

/**
 * 状态事件执行接口参数
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 3:32 PM
 */
@Data
public class StateEventActionParam<T> {

    private DefaultOrderStateEvent orderStateEvent;

    private DefaultFsmOrder fsmOrder;

    private T data;

}
