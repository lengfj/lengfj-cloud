package com.lengfj.cloud.osme.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.osme.dao.ScenarioDao;
import com.lengfj.cloud.osme.entity.Scenario;
import com.lengfj.cloud.osme.service.ScenarioService;
import org.springframework.stereotype.Service;

/**
 * 场景表(Scenario)表服务实现类
 *
 * @author lengfj
 * @since 2021-12-28 16:54:27
 */
@Service("scenarioService")
public class ScenarioServiceImpl extends ServiceImpl<ScenarioDao, Scenario> implements ScenarioService {

}
