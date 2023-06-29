package com.lengfj.cloud.dynamic.pagination.starter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 分页列表页面配置数据
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/11 9:38 AM
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageInfoFieldConfigVO extends PageInfoVO {

    private List<PageFieldInfoVO> fieldInfos;
}
