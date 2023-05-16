package com.lengfj.cloud.common.id.leaf;

import com.lengfj.cloud.common.core.util.SpringContextUtil;
import com.lengfj.cloud.common.id.IDGenerator;
import org.springframework.web.client.RestTemplate;

/**
 * leaf组件的号段id生成模式
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/6 10:07 AM
 */
public class LeafSegmentIDGenerator implements IDGenerator {

    private static volatile LeafSegmentIDGenerator INSTANCE;

    /**
     * 获取实例，保证实例是单例的
     * @return {@link LeafSegmentIDGenerator}
     */
    public static LeafSegmentIDGenerator getInstance(){
        if (INSTANCE == null){
            synchronized (LeafSegmentIDGenerator.class){
                if (INSTANCE == null){
                    INSTANCE = new LeafSegmentIDGenerator();
                }
            }
        }
        return INSTANCE;
    }

    private LeafSegmentIDGenerator(){
        this.restTemplate = SpringContextUtil.getBean(RestTemplate.class);
    }

    private RestTemplate restTemplate;

    @Override
    public String getId(String key) {
        return restTemplate.getForEntity(LeafConstants.SERVICE_URL + LeafConstants.API_SEGMENT + key,String.class).getBody();
    }
}
