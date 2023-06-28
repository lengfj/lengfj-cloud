package com.lengfj.cloud.osme.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengfj.cloud.osme.entity.Scenario;
import org.apache.ibatis.annotations.Mapper;

/**
 * 场景表(Scenario)表数据库访问层
 *
 * @author lengfj
 * @since 2021-12-28 16:54:27
 */
@Mapper
public interface ScenarioDao extends BaseMapper<Scenario> {

}
