package com.lengfj.cloud.dynamic.pagination.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 页面信息保存参数
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 2:40 PM
 */
@Data
public class PageInfoSaveDTO {

    /**
     * 页面id
     */
    private Integer id;
    /**
     * 页面code
     */
    private String pageCode;

    /**
     * 页面名称
     */
    private String pageName;

    /**
     * 页面路由
     */
    private String pageUrl;

    /**
     * 页面字段信息配置列表
     */
    private List<PageFieldInfoSaveDTO> pageFieldInfoSaves;
}
