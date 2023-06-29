package com.lengfj.cloud.dynamic.pagination.model.dto;

import lombok.Data;

/**
 * 添加/修改页面字段参数
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 7:40 PM
 */
@Data
public class PageListConfigAddFieldDTO {

    /**
     * 页面id
     */
    private Integer pageId;
    /**
     * 字段id
     */
    private Integer fieldId;
    /**
     * 是否展示
     */
    private Integer isShow;
    /**
     * 排序
     */
    private Integer sort;
}
