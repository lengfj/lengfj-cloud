package com.lengfj.cloud.dynamic.pagination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 页面配置
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:44 PM
 */
@Mapper
public interface PageConfigDao extends BaseMapper<PageConfigEntity> {

    /**
     * 查询配置组id列表
     * @param pageId 页面id
     * @return
     */
    List<Integer> selectConfigIds(Integer pageId);
}
