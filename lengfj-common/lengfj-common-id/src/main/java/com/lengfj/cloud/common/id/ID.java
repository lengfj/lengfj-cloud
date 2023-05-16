package com.lengfj.cloud.common.id;

import com.lengfj.cloud.common.core.exception.LengfjNullPointException;
import com.lengfj.cloud.common.core.exception.enums.DefaultExceptionEnum;
import com.lengfj.cloud.common.id.factory.IDGeneratorFactory;
import com.lengfj.cloud.common.id.factory.IDType;

/**
 * 分布式ID生成工具类
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/6 10:44 AM
 */
public class ID {

    /**
     * 获取id
     * @param idType id生成模式
     * @param key 业务类型
     * @return
     */
    public static String getId(IDType idType, String key){
        if (idType == null){
            throw new LengfjNullPointException(DefaultExceptionEnum.ID_EXCEPTION,"idType can not be empty");
        }
        return IDGeneratorFactory.getIdGenerator(idType).getId(key);
    }

    /**
     * 获取雪花模式生成的id
     * IDType：LEAF_SNOWFLAKE
     * @return 雪花模式生成的id
     */
    public static String getSnowflakeId(){
        return getId(IDType.LEAF_SNOWFLAKE,"lengfj");
    }

    /**
     * 获取号段模式生成的id
     * IDType: LEAF_SEGMENT
     * @param key 业务类型
     * @return 号段模式生成的id
     */
    public static String getSegmentId(String key){
        return getId(IDType.LEAF_SEGMENT,key);
    }
}
