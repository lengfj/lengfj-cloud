package com.lengfj.cloud.osme.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengfj.cloud.osme.entity.Event;
import org.apache.ibatis.annotations.Mapper;

/**
 * 事件表(Event)表数据库访问层
 *
 * @author lengfj
 * @since 2021-12-28 16:53:31
 */
@Mapper
public interface EventDao extends BaseMapper<Event> {

}
