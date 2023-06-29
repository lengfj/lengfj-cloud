package com.lengfj.cloud.dynamic.pagination.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lengfj.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面信息
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:15 PM
 */
@TableName("page_info")
@EqualsAndHashCode(callSuper = true)
@Data
public class PageInfoEntity extends BaseEntity {

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

    /**
     * 分页列表子code
     */
    private String subCode;

    /**
     * 分页列表子名称
     */
    private String subName;

    /**
     * 分页列表路由地址
     */
    private String pageUrl;

    /**
     * 是否删除
     */
    private Integer isDel;
}
