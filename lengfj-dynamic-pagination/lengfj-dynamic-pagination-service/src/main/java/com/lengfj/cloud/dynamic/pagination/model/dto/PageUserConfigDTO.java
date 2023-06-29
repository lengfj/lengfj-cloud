package com.lengfj.cloud.dynamic.pagination.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户配置页面参数
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 7:49 PM
 */
@Data
public class PageUserConfigDTO {

    /**
     * 用户主体id
     */
    private Long userId;

    /**
     * 页面id
     */
    private Integer pageId;

    /**
     * 配置组id
     */
    private Integer configId;

    /**
     * 页面配置
     */
    private List<PageListConfigSaveDTO> configSaveDtos;
}
