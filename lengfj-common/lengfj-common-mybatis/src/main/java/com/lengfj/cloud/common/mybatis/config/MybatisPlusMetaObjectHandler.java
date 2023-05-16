package com.lengfj.cloud.common.mybatis.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * MybatisPlus 自动填充配置
 * @author lester
 * @version V1.0
 * @date 2021/12/13
 **/
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        fillValIfNullByName("createTime", LocalDateTime.now(), metaObject, false);
//        fillValIfNullByName("createId", UserInfoUtil.getUserId(), metaObject, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, false);
//        fillValIfNullByName("updateId", UserInfoUtil.getUserId(), metaObject, false);
    }


    /**
     * 填充值，先判断是否有手动设置，优先手动设置的值
     * @param fieldName 属性名
     * @param fieldVal 属性值
     * @param metaObject MetaObject
     * @param isCover 是否覆盖原有值,避免更新操作手动入参
     */
    private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
        // 1. 没有 get set 方法
        if (!metaObject.hasSetter(fieldName) || !metaObject.hasGetter(fieldName)) {
            return;
        }
        // 2. 如果用户有手动设置的值
        Object userSetValue = metaObject.getValue(fieldName);
        String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
        if (StrUtil.isNotBlank(setValueStr) && !isCover) {
            return;
        }
        // 3. field 类型相同时设置
        Class<?> getterType = metaObject.getGetterType(fieldName);
        if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
            metaObject.setValue(fieldName, fieldVal);
        }
    }
}
