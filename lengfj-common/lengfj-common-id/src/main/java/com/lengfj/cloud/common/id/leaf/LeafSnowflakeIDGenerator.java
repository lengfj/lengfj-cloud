package com.lengfj.cloud.common.id.leaf;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import com.lengfj.cloud.common.id.IDGenerator;
import org.springframework.web.client.RestTemplate;

/**
 * leaf组件的雪花算法id生成模式
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/6 10:08 AM
 */
public class LeafSnowflakeIDGenerator implements IDGenerator {

    private static volatile LeafSnowflakeIDGenerator INSTANCE;

    /**
     * 获取实例，保证实例是单例的
     * @return {@link LeafSnowflakeIDGenerator}
     */
    public static LeafSnowflakeIDGenerator getInstance(){
        if (INSTANCE == null){
            synchronized (LeafSnowflakeIDGenerator.class){
                if (INSTANCE == null){
                    INSTANCE = new LeafSnowflakeIDGenerator();
                }
            }
        }
        return INSTANCE;
    }

    private LeafSnowflakeIDGenerator(){
        this.restTemplate = SpringContextUtil.getBean(RestTemplate.class);
    }

    private RestTemplate restTemplate;

    @Override
    public String getId(String key) {
        return restTemplate.getForEntity(LeafConstants.SERVICE_URL + LeafConstants.API_SNOWFLAKE + key,String.class).getBody();
    }
}
