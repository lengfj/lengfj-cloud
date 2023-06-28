package com.lengfj.cloud.osme.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengfj.cloud.osme.entity.State;
import org.apache.ibatis.annotations.Mapper;

/**
 * 状态表(State)表数据库访问层
 *
 * @author lengfj
 * @since 2021-12-28 16:54:32
 */
@Mapper
public interface StateDao extends BaseMapper<State> {

}
