package com.lengfj.cloud.common.mybatis.page;

import lombok.Data;

/**
 * 数据表头信息
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/26
 **/
@Data
public class TableHeader {

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
