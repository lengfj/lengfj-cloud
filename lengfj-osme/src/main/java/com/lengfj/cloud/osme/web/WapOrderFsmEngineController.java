package com.lengfj.cloud.osme.web;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.common.core.exception.base.BaseBusinessException;
import com.lengfj.cloud.osme.exception.OsmeException;
import com.lengfj.cloud.osme.exception.OsmeExceptionEnum;
import com.lengfj.cloud.osme.engine.OrderFsmEngine;
import com.lengfj.cloud.osme.module.CreateOrderDTO;
import com.lengfj.cloud.osme.module.ServiceResult;
import com.lengfj.cloud.osme.module.StateEventActionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单引擎接口
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/29 3:23 PM
 */
@RestController
@RequestMapping("/order/fsm/engine")
public class WapOrderFsmEngineController {

    @Autowired
    private OrderFsmEngine orderFsmEngine;


    @PostMapping("/actionEvent")
    public R actionEvent(@RequestBody StateEventActionParam<CreateOrderDTO> param){
        try {
            ServiceResult<R> serviceResult = orderFsmEngine.sendEvent(param.getOrderStateEvent(),param.getFsmOrder(), param.getData());
            return serviceResult.getData();
        }catch (BaseBusinessException e){
            throw e;
        }catch (Exception e) {
            e.printStackTrace();
            throw new OsmeException(OsmeExceptionEnum.SYSTEM_ERROR);
        }
    }

}
