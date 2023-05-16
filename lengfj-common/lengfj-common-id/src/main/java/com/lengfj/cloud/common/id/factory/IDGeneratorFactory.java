package com.lengfj.cloud.common.id.factory;

import com.lengfj.cloud.common.id.leaf.LeafSegmentIDGenerator;
import com.lengfj.cloud.common.id.leaf.LeafSnowflakeIDGenerator;
import com.lengfj.cloud.common.id.IDGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * ID生成模式工厂
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/6 1:05 PM
 */
public class IDGeneratorFactory {

    private final static Map<IDType, IDGenerator> idGeneratorMap = new HashMap<>();

    static {
        idGeneratorMap.put(IDType.LEAF_SEGMENT, LeafSegmentIDGenerator.getInstance());
        idGeneratorMap.put(IDType.LEAF_SNOWFLAKE, LeafSnowflakeIDGenerator.getInstance());
    }

    /**
     * 获取IDGenerator实例
     * @param idType ID生成模式
     * @return
     */
    public static IDGenerator getIdGenerator(IDType idType){
        return idGeneratorMap.get(idType);
    }
}
