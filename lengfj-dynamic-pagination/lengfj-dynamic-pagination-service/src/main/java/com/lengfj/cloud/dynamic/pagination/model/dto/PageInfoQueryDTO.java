package com.lengfj.cloud.dynamic.pagination.model.dto;

import com.lengfj.cloud.common.mybatis.page.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面信息查询参数
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 10:03 AM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageInfoQueryDTO extends PageParam {

    /**
     * 页面code
     */
    private String pageCode;
    /**
     * 页面名称
     */
    private String pageName;
}
