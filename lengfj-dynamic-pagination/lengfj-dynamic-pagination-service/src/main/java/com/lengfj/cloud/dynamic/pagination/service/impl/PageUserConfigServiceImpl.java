package com.lengfj.cloud.dynamic.pagination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.common.core.util.Assert;
import com.lengfj.cloud.dynamic.pagination.dao.PageUserConfigDao;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageUserConfigDTO;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageUserConfigEntity;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageFieldInfoVO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO;
import com.lengfj.cloud.dynamic.pagination.service.PageConfigService;
import com.lengfj.cloud.dynamic.pagination.service.PageFieldInfoService;
import com.lengfj.cloud.dynamic.pagination.service.PageUserConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面配置用户关联表
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:57 PM
 */
@Slf4j
@Service
public class PageUserConfigServiceImpl extends ServiceImpl<PageUserConfigDao, PageUserConfigEntity> implements PageUserConfigService {

    @Autowired
    private PageConfigService pageConfigService;
    @Autowired
    private PageFieldInfoService pageFieldInfoService;

    @Override
    public Integer getConfigId(Long userId, Integer pageId) {
        QueryWrapper<PageUserConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("page_id", pageId);
        PageUserConfigEntity entity = this.getOne(wrapper);
        if (entity != null){
            return entity.getConfigId();
        }
        return null;
    }

    @Override
    public List<PageListConfigVO> configList(Long userId, Integer pageId) {
        Assert.notNull(userId, "主体id");
        Assert.notNull(pageId, "页面id");
        Integer configId = getConfigId(userId, pageId);
        if (configId == null){
            //未自定义列表 获取默认配置
            return defaultConfigList(pageId);
        }
        //自定义列表
        return pageConfigService.list(configId);
    }

    @Override
    public List<PageListConfigVO> defaultConfigList(Integer pageId) {
        List<PageListConfigVO> pageListConfigs = new ArrayList<>();
        //获取默认配置
        List<PageFieldInfoVO> list = pageFieldInfoService.list(pageId, null);
        if (CollectionUtils.isEmpty(list)){
            return pageListConfigs;
        }
        for (PageFieldInfoVO fieldInfoVO : list) {
            PageListConfigVO configVo = new PageListConfigVO();
            configVo.setPageId(fieldInfoVO.getPageId());
            configVo.setFieldId(fieldInfoVO.getId());
            configVo.setFieldCode(fieldInfoVO.getFieldCode());
            configVo.setFieldName(fieldInfoVO.getFieldName());
            configVo.setFieldType(fieldInfoVO.getFieldType());
            configVo.setIsShow(fieldInfoVO.getFieldDefaultShow() == 1);
            configVo.setIsSortQuery(fieldInfoVO.getIsSortQuery() == 1);
            configVo.setSort(fieldInfoVO.getFieldDefaultSort());
            pageListConfigs.add(configVo);
        }
        return pageListConfigs;
    }

    @Override
    public void saveOrModify(PageUserConfigDTO configDto) {
        Long userId = configDto.getUserId();
        Integer pageId = configDto.getPageId();
        Integer configId = configDto.getConfigId();
        List<PageListConfigSaveDTO> configSaveDtos = configDto.getConfigSaveDtos();

        if (configId == null) {
            //查询是否有配置
            configId = this.getConfigId(userId, pageId);
        }
        if (configId == null){
            //用户未自定义过列表 新增
            configId = pageConfigService.save(pageId, configSaveDtos);
            PageUserConfigEntity pageUserConfigEntity = new PageUserConfigEntity();
            pageUserConfigEntity.setUserId(userId);
            pageUserConfigEntity.setPageId(pageId);
            pageUserConfigEntity.setConfigId(configId);
            this.save(pageUserConfigEntity);

            //保存字段
            pageConfigService.save(pageId, configSaveDtos);
        }else{
            //修改配置
            pageConfigService.modify(configSaveDtos);
        }
    }
}
