package com.lengfj.cloud.osme.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.osme.dao.ProcessEventStateScenarioDao;
import com.lengfj.cloud.osme.entity.ProcessEventStateScenario;
import com.lengfj.cloud.osme.service.ProcessEventStateScenarioService;
import org.springframework.stereotype.Service;

/**
 * 流程事件状态场景关联表(ProcessEventStateScenario)表服务实现类
 *
 * @author lengfj
 * @since 2021-12-28 16:54:21
 */
@Service("processEventStateScenarioService")
public class ProcessEventStateScenarioServiceImpl extends ServiceImpl<ProcessEventStateScenarioDao, ProcessEventStateScenario> implements ProcessEventStateScenarioService {

}
