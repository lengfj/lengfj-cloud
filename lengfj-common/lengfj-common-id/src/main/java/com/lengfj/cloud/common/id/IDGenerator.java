package com.lengfj.cloud.common.id;

/**
 * id生成器
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/6 10:03 AM
 */
public interface IDGenerator {
    /**
     * 获取id 返回String类型
     * @param key 业务类型
     * @return id
     */
    String getId(String key);
}
