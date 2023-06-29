package com.lengfj.cloud.dynamic.pagination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lengfj.cloud.common.core.util.Assert;
import com.lengfj.cloud.dynamic.pagination.dao.PageFieldInfoDao;
import com.lengfj.cloud.dynamic.pagination.exception.DynamicPageListException;
import com.lengfj.cloud.dynamic.pagination.exception.DynamicPageListExceptionEnum;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageFieldInfoSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigAddFieldDTO;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageFieldInfoEntity;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageFieldInfoVO;
import com.lengfj.cloud.dynamic.pagination.service.PageConfigService;
import com.lengfj.cloud.dynamic.pagination.service.PageFieldInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 页面字段信息
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:44 PM
 */
@Service
public class PageFieldInfoServiceImpl extends ServiceImpl<PageFieldInfoDao, PageFieldInfoEntity> implements PageFieldInfoService {

    @Autowired
    private PageConfigService pageConfigService;

    @Override
    public List<PageFieldInfoVO> list(Integer pageId, String fieldCode) {
        QueryWrapper<PageFieldInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(pageId != null, "page_id", pageId);
        wrapper.eq(StringUtils.isNotBlank(fieldCode), "field_code", fieldCode);
        wrapper.eq("is_del", 0);
        List<PageFieldInfoVO> pageFieldInfoVOList = new ArrayList<>();
        for (PageFieldInfoEntity pageFieldInfoEntity : this.list(wrapper)) {
            PageFieldInfoVO pageFieldInfoVO = new PageFieldInfoVO();
            BeanUtils.copyProperties(pageFieldInfoEntity, pageFieldInfoVO);
            pageFieldInfoVOList.add(pageFieldInfoVO);
        }
        return pageFieldInfoVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer save(PageFieldInfoSaveDTO saveDTO) {
        //1. 参数校验
        Assert.notNull(saveDTO);
        Assert.notNull(saveDTO.getPageId(), "页面id");
        Assert.notEmpty(saveDTO.getFieldCode(), "字段code");
        Assert.notEmpty(saveDTO.getFieldName(), "字段名称");
        Assert.notNull(saveDTO.getFieldSortCode(),"默认字段排序");
        //2. 数据重复校验
        if (checkRepeat(saveDTO.getPageId(), null , saveDTO.getFieldCode())){
            throw new DynamicPageListException(DynamicPageListExceptionEnum.BDPL_PAGE_FIELD_REPEAT_ERROR);
        }
        //3. 保存数据
        PageFieldInfoEntity pageFieldInfoEntity = new PageFieldInfoEntity();
        pageFieldInfoEntity.setFieldCode(saveDTO.getFieldCode());
        pageFieldInfoEntity.setFieldName(saveDTO.getFieldName());
        pageFieldInfoEntity.setPageId(saveDTO.getPageId());
        pageFieldInfoEntity.setFieldType(saveDTO.getFieldType());
        pageFieldInfoEntity.setFieldSortCode(saveDTO.getFieldSortCode());
        pageFieldInfoEntity.setFieldDefaultSort(saveDTO.getFieldDefaultSort());
        pageFieldInfoEntity.setIsSortQuery(saveDTO.getIsSortQuery());
        this.save(pageFieldInfoEntity);
        return pageFieldInfoEntity.getPageId();
    }

    @Override
    public void modify(PageFieldInfoSaveDTO modifyDto) {
        //1. 参数校验
        Assert.notNull(modifyDto);
        Assert.notNull(modifyDto.getPageId(), "页面id");
        Assert.notEmpty(modifyDto.getFieldCode(), "字段code");
        Assert.notEmpty(modifyDto.getFieldName(), "字段名称");
        Assert.notNull(modifyDto.getFieldSortCode(),"默认字段排序");

        //3. 保存数据
        PageFieldInfoEntity pageFieldInfoEntity = new PageFieldInfoEntity();
        pageFieldInfoEntity.setFieldCode(modifyDto.getFieldCode());
        pageFieldInfoEntity.setFieldName(modifyDto.getFieldName());
        pageFieldInfoEntity.setFieldType(modifyDto.getFieldType());
        pageFieldInfoEntity.setFieldSortCode(modifyDto.getFieldSortCode());
        pageFieldInfoEntity.setFieldDefaultSort(modifyDto.getFieldDefaultSort());
        pageFieldInfoEntity.setIsSortQuery(modifyDto.getIsSortQuery());

        UpdateWrapper<PageFieldInfoEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("page_id", modifyDto.getPageId());
        wrapper.eq("field_code", modifyDto.getFieldCode());
        wrapper.eq("is_del", 0);
        this.update(pageFieldInfoEntity, wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer pageId, String fieldCode) {
        Assert.notNull(pageId,"页面id");
        UpdateWrapper<PageFieldInfoEntity> wrapper = new UpdateWrapper<>();
        wrapper.eq("page_id", pageId);
        wrapper.eq(StringUtils.isNotBlank(fieldCode), "field_code", fieldCode);
        wrapper.eq("is_del", 0);

        PageFieldInfoEntity entity = new PageFieldInfoEntity();
        entity.setIsDel(1);
        entity.setUpdateTime(LocalDateTime.now());
        entity.setUpdater("");
        this.update(entity, wrapper);
    }

    @Override
    public void delete(Integer id) {
        PageFieldInfoEntity entity = new PageFieldInfoEntity();
        entity.setId(id);
        entity.setIsDel(1);
        entity.setUpdateTime(LocalDateTime.now());
        entity.setUpdater("");
        this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyPageFields(Integer pageId, List<PageFieldInfoSaveDTO> fieldDtos) {
        /*
            注意：修改配置影响较大
            4.1. 获取已有字段配置
            4.2. 根据field_code判断是否是新增的字段
            4.3. 获取已有配置和本次配置比较是否有删除的字段
            4.4. 异步新增 and 异步删除
         */
        List<String> delFieldCode = new ArrayList<>();
        List<String> addFieldCode = new ArrayList<>();
        //4.1. 获取已有字段配置
        List<PageFieldInfoVO> fieldInfoVos = this.list(pageId, null);
        if (fieldDtos == null){
            //本次配置为空 表示字段全部删除
            fieldDtos = new ArrayList<>();
        }

        //4.2. 根据field_code判断是否是新增的字段
        for (PageFieldInfoSaveDTO fieldSave : fieldDtos){
            boolean noneMatch = fieldInfoVos.stream().noneMatch(x -> fieldSave.getFieldCode().equals(x.getFieldCode()));
            if (noneMatch){
                //没有一个匹配的 新增
                addFieldCode.add(fieldSave.getFieldCode());
            }
        }

        //4.3. 获取已有配置和本次配置比较是否有删除的字段
        for (PageFieldInfoVO fieldInfoVo : fieldInfoVos) {
            boolean noneMatch = fieldDtos.stream().noneMatch(x -> fieldInfoVo.getFieldCode().equals(x.getFieldCode()));
            if (noneMatch){
                //没有一个匹配的 删除
                delFieldCode.add(fieldInfoVo.getFieldCode());
            }
        }

        //4. 新增页面字段列表
        if (CollectionUtils.isNotEmpty(fieldDtos)){
            for (PageFieldInfoSaveDTO fieldInfo : fieldDtos) {
                fieldInfo.setPageId(pageId);
                if (addFieldCode.contains(fieldInfo.getFieldCode())){
                    //新增
                    Integer id = this.save(fieldInfo);
                    //所有配置组中新增字段
                    PageListConfigAddFieldDTO addFieldDto = new PageListConfigAddFieldDTO();
                    addFieldDto.setFieldId(id);
                    addFieldDto.setPageId(pageId);
                    addFieldDto.setIsShow(fieldInfo.getFieldDefaultShow());
                    addFieldDto.setSort(fieldInfo.getFieldDefaultSort());
                    pageConfigService.addField(addFieldDto);
                }else if (delFieldCode.contains(fieldInfo.getFieldCode())){
                    //删除
                    Optional<PageFieldInfoVO> delFieldInfo = fieldInfoVos.stream().filter(x -> x.getFieldCode().equals(fieldInfo.getFieldCode())).findFirst();
                    if (delFieldInfo.isPresent()){
                        //删除字段
                        Integer id = delFieldInfo.get().getId();
                        this.delete(id);
                        pageConfigService.delField(fieldInfo.getPageId(), id);
                    }
                }else{
                    //修改
                    this.modify(fieldInfo);
                }
            }
        }
    }

    /**
     * 校验同一个页面下字段是否重复
     * @param pageId 页面id
     * @param fieldId 字段id（排除的）
     * @param fieldCode 字段code
     * @return 是否重复
     */
    public boolean checkRepeat(Integer pageId, Integer fieldId, String fieldCode){
        //2. 数据重复校验
        QueryWrapper<PageFieldInfoEntity> wrapper = new QueryWrapper<>();
        //排除id
        wrapper.ne(fieldId != null, "id", fieldId);
        wrapper.eq("page_id", pageId);
        wrapper.eq("field_code", fieldCode);
        wrapper.eq("is_del", 0);
        return this.count(wrapper) > 0;
    }

}
