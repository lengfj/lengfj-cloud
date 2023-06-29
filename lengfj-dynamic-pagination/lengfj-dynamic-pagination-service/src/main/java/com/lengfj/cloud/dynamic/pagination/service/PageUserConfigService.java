package com.lengfj.cloud.dynamic.pagination.service;


import com.lengfj.cloud.dynamic.pagination.model.dto.PageUserConfigDTO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO;

import java.util.List;

/**
 * 页面配置用户关联表
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:47 PM
 */
public interface PageUserConfigService{

    /***
     * 获取配置id
     *
     * @param userId 用户主体id
     * @param pageId 页面id
     * @return {@link Integer} 配置id
     * @author lengfj
     * @date 2022/1/11 7:48 PM
     */
    Integer getConfigId(Long userId, Integer pageId);

    /**
     * 获取用户页面配置列表
     * @param userId 主体id
     * @param pageId 页面id
     * @return 用户页面配置
     * @author lengfj
     * @date 2022/1/11 7:48 PM
     */
    List<PageListConfigVO> configList(Long userId, Integer pageId);

    /**
     * 获取默认的页面配置列表
     * @param pageId 页面id
     * @return 默认页面配置
     * @author lengfj
     * @date 2022/1/11 7:48 PM
     */
    List<PageListConfigVO> defaultConfigList(Integer pageId);

    /***
     * 保存或修改
     *
     * @param configDto 参数
     * @author lengfj
     * @date 2022/1/11 7:50 PM
     */
    void saveOrModify(PageUserConfigDTO configDto);
}
