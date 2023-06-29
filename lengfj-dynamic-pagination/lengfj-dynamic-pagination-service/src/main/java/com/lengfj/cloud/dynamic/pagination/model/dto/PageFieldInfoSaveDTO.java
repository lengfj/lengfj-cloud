package com.lengfj.cloud.dynamic.pagination.model.dto;

import lombok.Data;

/**
 * 页面字段信息保存参数
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 2:47 PM
 */
@Data
public class PageFieldInfoSaveDTO {

    /**
     * Id
     */
    private Integer id;
    /**
     * 页面id
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
}
