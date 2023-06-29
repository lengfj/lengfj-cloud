package com.lengfj.cloud.osme.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.osme.dao.StateDao;
import com.lengfj.cloud.osme.service.StateService;
import com.lengfj.cloud.osme.entity.State;
import org.springframework.stereotype.Service;

/**
 * 状态表(State)表服务实现类
 *
 * @author lengfj
 * @since 2021-12-28 16:54:33
 */
@Service("stateService")
public class StateServiceImpl extends ServiceImpl<StateDao, State> implements StateService {

}
