package com.lengfj.cloud.dynamic.pagination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.common.core.util.Assert;
import com.lengfj.cloud.common.id.ID;
import com.lengfj.cloud.dynamic.pagination.dao.PageConfigDao;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigAddFieldDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageConfigEntity;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO;
import com.lengfj.cloud.dynamic.pagination.service.PageConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面配置
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:44 PM
 */
@Slf4j
@Service
public class PageConfigServiceImpl extends ServiceImpl<PageConfigDao, PageConfigEntity> implements PageConfigService {

    @Autowired
    private PageConfigDao pageConfigDao;

    @Override
    public List<PageListConfigVO> list(Integer configId) {
        QueryWrapper<PageConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("config_id", configId);
        List<PageConfigEntity> pageConfigs = this.list(wrapper);
        List<PageListConfigVO> configVos = new ArrayList<>();
        for (PageConfigEntity pageConfig : pageConfigs) {
            PageListConfigVO configVO = new PageListConfigVO();
            BeanUtils.copyProperties(pageConfig, configVO);
            configVos.add(configVO);
        }
        return configVos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer save(PageListConfigSaveDTO saveDto) {
        Assert.notNull(saveDto);
        Assert.notNull(saveDto.getConfigId(),"配置id");
        Assert.notNull(saveDto.getPageId(),"页面id");
        Assert.notNull(saveDto.getFieldId(), "字段id");
        Assert.notNull(saveDto.getIsShow(), "是否展示");
        Assert.notNull(saveDto.getSort(), "排序");

        PageConfigEntity pageConfigEntity = new PageConfigEntity();
        BeanUtils.copyProperties(saveDto, pageConfigEntity);
        pageConfigEntity.setCreator("");
        pageConfigEntity.setCreateTime(LocalDateTime.now());
        this.save(pageConfigEntity);
        return pageConfigEntity.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer save(Integer pageId, List<PageListConfigSaveDTO> saveDtos) {
        /*
            1. 校验是否有重复的配置
            2. 生成configId
            3. 逐个保存
         */
        Integer configId = this.checkRepeat(pageId, saveDtos);
        if (configId != null){
            //有重复的直接返回配置id
            return configId;
        }
        configId = Integer.valueOf(ID.getSegmentId("page_config_id"));
        for (PageListConfigSaveDTO saveDto : saveDtos) {
            saveDto.setPageId(pageId);
            this.save(saveDto);
        }
        return configId;
    }

    @Override
    public void modify(PageListConfigSaveDTO modifyDto) {
        Assert.notNull(modifyDto);
        PageConfigEntity pageConfigEntity = new PageConfigEntity();
        pageConfigEntity.setUpdater("");
        pageConfigEntity.setUpdateTime(LocalDateTime.now());
        pageConfigEntity.setIsShow(modifyDto.getIsShow());
        pageConfigEntity.setSort(modifyDto.getSort());
        if (modifyDto.getId() != null){
            //根据id修改
            pageConfigEntity.setId(modifyDto.getId());
            this.updateById(pageConfigEntity);
        } else {
          //根据configId pageId fieldId 修改
            UpdateWrapper<PageConfigEntity> wrapper = new UpdateWrapper<>();
            wrapper.eq("config_id", modifyDto.getConfigId());
            wrapper.eq("page_id", modifyDto.getPageId());
            wrapper.eq("field_id", modifyDto.getFieldId());
            this.update(pageConfigEntity, wrapper);
        }
    }

    @Override
    public void modify(List<PageListConfigSaveDTO> saveDtos) {
        for (PageListConfigSaveDTO dto : saveDtos) {
            modify(dto);
        }
    }

    @Override
    public Integer checkRepeat(Integer pageId, List<PageListConfigSaveDTO> checkDtos) {
        /*
            校验逻辑
            查询页面下所有的配置,组装
         */
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delField(Integer pageId, Integer fieldId) {
        UpdateWrapper<PageConfigEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("page_id", pageId);
        wrapper.eq("field_id", fieldId);
        Integer delCount = pageConfigDao.delete(wrapper);
        log.info("删除页面[{}]下字段id[{}],删除数量：{}", pageId, fieldId, delCount);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addField(PageListConfigAddFieldDTO addFieldDto) {

        List<Integer> configIds = pageConfigDao.selectConfigIds(addFieldDto.getPageId());
        if (CollectionUtils.isEmpty(configIds)){
            return;
        }
        log.info("新增页面[{}]字段[{}]，获取到配置数量:{}", addFieldDto.getPageId(), addFieldDto.getFieldId(), configIds.size());
        configIds.forEach(c -> {
            PageListConfigSaveDTO saveDTO = new PageListConfigSaveDTO();
            saveDTO.setConfigId(c);
            saveDTO.setPageId(addFieldDto.getPageId());
            saveDTO.setFieldId(addFieldDto.getFieldId());
            saveDTO.setIsShow(addFieldDto.getIsShow());
            saveDTO.setSort(addFieldDto.getSort());
            this.save(saveDTO);
        });
        log.info("新增页面[{}]字段[{}]，操作成功!!!", addFieldDto.getPageId(), addFieldDto.getFieldId());
    }
}
