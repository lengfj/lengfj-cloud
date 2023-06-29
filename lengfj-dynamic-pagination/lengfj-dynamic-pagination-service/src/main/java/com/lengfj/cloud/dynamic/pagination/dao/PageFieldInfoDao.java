package com.lengfj.cloud.dynamic.pagination.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageFieldInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 页面字段信息
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:46 PM
 */
@Mapper
public interface PageFieldInfoDao extends BaseMapper<PageFieldInfoEntity> {
}
