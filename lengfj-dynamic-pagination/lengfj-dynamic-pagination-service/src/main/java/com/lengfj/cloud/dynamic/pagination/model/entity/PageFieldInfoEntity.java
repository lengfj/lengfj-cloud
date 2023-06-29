package com.lengfj.cloud.dynamic.pagination.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lengfj.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面字段信息
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:15 PM
 */
@TableName("page_field_info")
@EqualsAndHashCode(callSuper = true)
@Data
public class PageFieldInfoEntity extends BaseEntity {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 列表页面id
     */
    private Integer pageId;

    /**
     * 字段code
     */
    private String fieldCode;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段排序code
     */
    private String fieldSortCode;

    /**
     * 默认排序
     */
    private Integer fieldDefaultSort;

    /**
     * 默认显示 1是 0否
     */
    private Integer fieldDefaultShow;

    /**
     * 字段是否可以排序查询
     */
    private Integer isSortQuery;

    /**
     * 是否删除
     */
    private Integer isDel;
}
