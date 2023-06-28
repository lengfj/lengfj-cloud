package com.lengfj.cloud.osme.service.plugin;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.osme.engine.StateContext;
import com.lengfj.cloud.osme.module.CreateOrderDTO;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.plugin.PluginHandler;
import com.lengfj.cloud.osme.plugin.ProcessorPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 买家下单插件
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/30 1:33 PM
 */
@Slf4j
@Component
@ProcessorPlugin(state = "0",event = "1",bizCode = "1",sceneId = "1")
public class BuyerCreateOrderPlugin implements PluginHandler<R,CreateOrderDTO> {

    @Override
    public ServiceResult<R> action(StateContext<CreateOrderDTO> context) throws Exception {
        log.info("buyer create plugin exec...");
        if (context.getContext().getId().equals(10L)){
            return new ServiceResult<>(500,false, R.fail("插件干扰事件执行，返回错误"));
        }
        return new ServiceResult<R>(200,true,R.success("ok"));
    }
}
