package com.lengfj.cloud.osme.service.processor.custom.create;

import com.lengfj.cloud.osme.service.processor.CreateOrderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商家创建订单
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/30 1:21 PM
 */
@Slf4j
@Service
public class MerchantCreateOrderProcessor extends CreateOrderProcessor {

    @Override
    protected String custom() {
        log.info("商家创建订单扩展方法执行--");
        return "merchant create";
    }
}
