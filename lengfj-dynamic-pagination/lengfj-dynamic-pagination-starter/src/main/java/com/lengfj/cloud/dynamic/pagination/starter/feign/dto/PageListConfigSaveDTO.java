package com.lengfj.cloud.dynamic.pagination.starter.feign.dto;

import lombok.Data;

/**
 * 分页列表配置保存参数
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 7:21 PM
 */
@Data
public class PageListConfigSaveDTO {

        /**
         * 配置主键id
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
         * 是否展示 0不展示 1展示
         */
        private Integer isShow;

        /**
         * 排序
         */
        private Integer sort;
}
