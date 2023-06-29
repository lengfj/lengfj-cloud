package com.lengfj.cloud.dynamic.pagination.model.vo;

import lombok.Data;

/**
 * 页面信息配置VO
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 9:13 AM
 */
@Data
public class PageInfoVO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 分页列表code
     */
    private String pageCode;

    /**
     * 分页列表名称
     */
    private String pageName;

//    /**
//     * 分页列表子code
//     */
//    private String subCode;
//
//    /**
//     * 分页列表子名称
//     */
//    private String subName;

    /**
     * 分页列表路由地址
     */
    private String pageUrl;
}
