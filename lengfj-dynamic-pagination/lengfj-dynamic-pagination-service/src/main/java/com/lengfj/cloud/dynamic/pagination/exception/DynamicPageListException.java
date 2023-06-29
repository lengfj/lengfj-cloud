package com.lengfj.cloud.dynamic.pagination.exception;

import com.lengfj.cloud.common.core.exception.base.AbstractExceptionEnum;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;
import com.lengfj.cloud.common.core.util.SpringContextUtil;

/**
 * 动态分页列表组件模块异常
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 2:34 PM
 */
public class DynamicPageListException extends BaseBusinessException {

    public DynamicPageListException(AbstractExceptionEnum exception, Object... objs) {
        super(SpringContextUtil.APP_NAME, exception, objs);
    }
}
