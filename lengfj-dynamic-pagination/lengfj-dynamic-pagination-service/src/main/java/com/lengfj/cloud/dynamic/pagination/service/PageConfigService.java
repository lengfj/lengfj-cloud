package com.lengfj.cloud.dynamic.pagination.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigAddFieldDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageConfigEntity;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO;

import java.util.List;

/**
 * 页面配置
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:44 PM
 */
public interface PageConfigService extends IService<PageConfigEntity> {

    /***
     * 查询页面配置列表
     *
     * @param configId
     * @return {@link List<com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO>}
     * @author lengfj
     * @date 2022/1/11 7:20 PM
     */
    List<PageListConfigVO> list(Integer configId);

    /***
     * 保存
     *
     * @param saveDto 配置
     * @return
     * @author lengfj
     * @date 2022/1/11 7:22 PM
     */
    Integer save(PageListConfigSaveDTO saveDto);

    /***
     * 保存列表
     * @param pageId 页面id
     * @param saveDtos 配置list
     * @return {@link Integer} 配置组id
     * @author lengfj
     * @date 2022/1/11 7:22 PM
     */
    Integer save(Integer pageId, List<PageListConfigSaveDTO> saveDtos);

    /**
     * 修改配置
     * @param saveDto 配置
     */
    void modify(PageListConfigSaveDTO saveDto);

    /**
     * 修改配置
     * @param saveDtos 配置列表
     */
    void modify(List<PageListConfigSaveDTO> saveDtos);

    /***
     * 校验重复
     * @param pageId 页面id
     * @param checkDtos 校验的参数
     * @return {@link Integer} 配置组id
     * @author lengfj
     * @date 2022/1/11 7:25 PM
     */
    Integer checkRepeat(Integer pageId, List<PageListConfigSaveDTO> checkDtos);

    /***
     * 删除页面字段
     *
     * @param pageId 页面id
     * @param fieldId 字段id
     * @return
     * @author lengfj
     * @date 2022/1/11 7:37 PM
     */
    void delField(Integer pageId, Integer fieldId);

    /***
     * 添加页面字段
     *
     * @param addFieldDto 添加字段参数
     * @return
     * @author lengfj
     * @date 2022/1/11 7:37 PM
     */
    void addField(PageListConfigAddFieldDTO addFieldDto);
}
