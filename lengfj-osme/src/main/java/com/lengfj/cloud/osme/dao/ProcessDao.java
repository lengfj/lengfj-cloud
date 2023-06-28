package com.lengfj.cloud.osme.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.lengfj.cloud.osme.entity.Process;

/**
 * 流程表(Process)表数据库访问层
 *
 * @author lengfj
 * @since 2021-12-28 16:54:11
 */
@Mapper
public interface ProcessDao extends BaseMapper<Process> {

}
