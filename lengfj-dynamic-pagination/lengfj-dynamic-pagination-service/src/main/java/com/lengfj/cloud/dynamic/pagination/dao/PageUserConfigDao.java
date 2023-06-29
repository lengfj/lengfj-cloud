package com.lengfj.cloud.dynamic.pagination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageUserConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 页面配置用户关联表
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:47 PM
 */
@Mapper
public interface PageUserConfigDao extends BaseMapper<PageUserConfigEntity> {
}
