package com.lengfj.cloud.osme.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengfj.cloud.osme.entity.ProcessEventStateScenario;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程事件状态场景关联表(ProcessEventStateScenario)表数据库访问层
 *
 * @author lengfj
 * @since 2021-12-28 16:54:21
 */
@Mapper
public interface ProcessEventStateScenarioDao extends BaseMapper<ProcessEventStateScenario> {

}
