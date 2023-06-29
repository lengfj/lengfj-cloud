package com.lengfj.cloud.dynamic.pagination.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lengfj.cloud.common.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 页面配置用户关联表
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:16 PM
 */
@TableName("page_user_config")
@EqualsAndHashCode(callSuper = true)
@Data
public class PageUserConfigEntity extends BaseEntity {

    private Integer id;

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

}
