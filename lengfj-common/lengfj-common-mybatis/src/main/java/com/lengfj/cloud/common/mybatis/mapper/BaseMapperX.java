package com.lengfj.cloud.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.github.pagehelper.PageInfo;
import com.lengfj.cloud.common.mybatis.constant.MyBatisConstant;
import com.lengfj.cloud.common.mybatis.page.PageParam;
import com.lengfj.cloud.common.mybatis.page.PageUtil;
import com.lengfj.cloud.common.mybatis.page.PageView;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

import static com.lengfj.cloud.common.mybatis.enums.DelEnum.NO;

/**
 * 在 BaseMapper 的基础上拓展，提供更多的能力
 * @author lester
 * @date 2022/1/19 1:39 下午
 */
public interface BaseMapperX<T> extends BaseMapper<T> {

    /**
     * 批量插入，适合大量数据插入
     *
     * @param entities 实体们
     */
    default void insertBatch(Collection<T> entities) {
        Db.saveBatch(entities);
    }

    /**
     * 批量插入，适合大量数据插入
     *
     * @param entities 实体们
     * @param size     插入数量 Db.saveBatch 默认为 1000
     */
    default void insertBatch(Collection<T> entities, int size) {
        Db.saveBatch(entities, size);
    }

    default void updateBatch(T update) {
        update(update, new QueryWrapper<>());
    }

    default void updateBatch(Collection<T> entities, int size) {
        Db.updateBatchById(entities, size);
    }


    default PageView<T> selectPage(PageParam pageParam, @Param("ew") Wrapper<T> queryWrapper) {
        // MyBatis Plus 查询
        PageUtil.startPage(pageParam);
        List<T> ts = selectList(queryWrapper);
        // 转换返回
        return PageView.of(PageInfo.of(ts));
    }

    default Long selectCount(boolean hasDel) {
        return selectCount(new QueryWrapper<T>().eq(hasDel, MyBatisConstant.IS_DEL, NO));
    }

    default Long selectCount(boolean hasDel, String field, Object value) {
        return selectCount(new QueryWrapper<T>().eq(field, value).eq(hasDel, MyBatisConstant.IS_DEL, NO));
    }

    default Long selectCount(SFunction<T, ?> field, Object value) {
        return selectCount(new LambdaQueryWrapper<T>().eq(field, value));
    }

    default boolean isExist(boolean hasDel, String field, Object value){
        return selectCount(hasDel, field, value) > 0;
    }


}
