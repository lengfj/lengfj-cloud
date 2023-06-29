package com.lengfj.cloud.dynamic.pagination.starter;

import lombok.Data;

/**
 * 分页列表配置
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 7:17 PM
 */
@Data
public class PageListConfigVO {

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
     * 是否可以排序查询
     */
    private Boolean isSortQuery;

    /**
     * 是否展示 0不展示 1展示
     */
    private Boolean isShow;

    /**
     * 排序
     */
    private Integer sort;
}
