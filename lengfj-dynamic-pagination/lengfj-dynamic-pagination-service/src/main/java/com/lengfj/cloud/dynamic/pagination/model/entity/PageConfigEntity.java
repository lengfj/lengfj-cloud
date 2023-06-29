package com.lengfj.cloud.dynamic.pagination.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lengfj.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面配置
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:15 PM
 */
@TableName("page_config")
@EqualsAndHashCode(callSuper = true)
@Data
public class PageConfigEntity extends BaseEntity {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 配置组id
     */
    private Integer configId;

    /**
     * 列表页面id
     */
    private Integer pageId;

    /**
     * 字段id
     */
    private Integer fieldId;

    /**
     * 是否展示 0不展示 1展示
     */
    private Integer isShow;

    /**
     * 排序
     */
    private Integer sort;
}
