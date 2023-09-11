package com.lengfj.cloud.dynamic.pagination.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.lengfj.cloud.common.core.exception.enums.DefaultExceptionEnum;
import com.lengfj.cloud.common.core.util.Assert;
import com.lengfj.cloud.common.mybatis.page.PageUtil;
import com.lengfj.cloud.common.mybatis.page.PageView;
import com.lengfj.cloud.common.redis.lock.LenLock;
import com.lengfj.cloud.dynamic.pagination.dao.PageInfoDao;
import com.lengfj.cloud.dynamic.pagination.exception.DynamicPageListException;
import com.lengfj.cloud.dynamic.pagination.exception.DynamicPageListExceptionEnum;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageFieldInfoSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageInfoQueryDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageInfoSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.entity.PageInfoEntity;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoFieldConfigVO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoVO;
import com.lengfj.cloud.dynamic.pagination.service.PageFieldInfoService;
import com.lengfj.cloud.dynamic.pagination.service.PageInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面信息
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2022/1/10 6:56 PM
 */
@Slf4j
@Service
public class PageInfoServiceImpl extends ServiceImpl<PageInfoDao, PageInfoEntity> implements PageInfoService {

    @Autowired
    private PageInfoDao pageInfoDao;
    @Autowired
    private PageFieldInfoService pageFieldInfoService;


    @Override
    public List<PageInfoVO> list(String pageCode, String pageName) {
        QueryWrapper<PageInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(pageCode), "page_code", pageCode);
        wrapper.eq(StringUtils.isNotBlank(pageName), "page_name", pageName);
        wrapper.eq("is_del",0);
        List<PageInfoVO> pageInfoVOList = new ArrayList<>();
        for (PageInfoEntity pageInfoEntity : this.list(wrapper)) {
            PageInfoVO pageInfoVO = new PageInfoVO();
            BeanUtils.copyProperties(pageInfoEntity, pageInfoVO);
            pageInfoVOList.add(pageInfoVO);
        }
        return pageInfoVOList;
    }

    @Override
    public PageView<PageInfoVO> pageList(PageInfoQueryDTO queryDTO) {
        PageUtil.startPage(queryDTO);
        List<PageInfoVO> list = this.list(queryDTO.getPageCode(), queryDTO.getPageName());
        PageInfo<PageInfoVO> pageInfo = new PageInfo<>(list);
        return PageView.of(pageInfo);
    }

    @Override
    public PageInfoFieldConfigVO detail(Integer id, String pageCode) {
        QueryWrapper<PageInfoEntity> wrapper = new QueryWrapper<>();
        boolean paramEmpty = true;
        if (id != null){
            paramEmpty  = false;
            wrapper.eq("id", id);
        }
        if (StringUtils.isNotBlank(pageCode)){
            paramEmpty = false;
            wrapper.eq("page_code", pageCode);
            wrapper.eq("is_del", 0);
        }
        //参数是空的
        if (paramEmpty){
            throw new DynamicPageListException(DefaultExceptionEnum.ASSERT_NOT_NULL_EXCEPTION, "id 或 页面code");
        }
        PageInfoEntity pageInfoEntity = this.getOne(wrapper);
        if (pageInfoEntity == null){
            return null;
        }
        //组装
        PageInfoFieldConfigVO pageInfoFieldConfigVO = new PageInfoFieldConfigVO();
        pageInfoFieldConfigVO.setId(pageInfoEntity.getId());
        pageInfoFieldConfigVO.setPageCode(pageInfoEntity.getPageCode());
        pageInfoFieldConfigVO.setPageName(pageInfoEntity.getPageName());
        pageInfoFieldConfigVO.setPageUrl(pageInfoEntity.getPageUrl());
        //字段信息
        pageInfoFieldConfigVO.setFieldInfos(pageFieldInfoService.list(pageInfoEntity.getId(), null));
        return pageInfoFieldConfigVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @LenLock(key = "'page-info-save-'.concat(#saveDTO.pageCode)")
    public void save(PageInfoSaveDTO saveDTO) {
        //1. 参数校验
        Assert.notNull(saveDTO);
        Assert.notEmpty(saveDTO.getPageCode());
        Assert.notEmpty(saveDTO.getPageName());
        //2. 数据重复校验
        List<PageInfoVO> pageInfos = this.list(saveDTO.getPageCode(), null);
        if (pageInfos.size() > 0){
            throw new DynamicPageListException(DynamicPageListExceptionEnum.BDPL_PAGE_REPEAT_ERROR);
        }

        //3. 新增分页页面
        PageInfoEntity pageInfoEntity = new PageInfoEntity();
        pageInfoEntity.setPageCode(saveDTO.getPageCode());
        pageInfoEntity.setPageName(saveDTO.getPageName());
        pageInfoEntity.setPageUrl(saveDTO.getPageUrl());
        pageInfoEntity.setCreator("");
        pageInfoEntity.setCreateTime(LocalDateTime.now());
        this.save(pageInfoEntity);
        //4. 新增页面字段列表
        if (CollectionUtils.isNotEmpty(saveDTO.getPageFieldInfoSaves())){
            for (PageFieldInfoSaveDTO pageFieldInfoSave : saveDTO.getPageFieldInfoSaves()) {
                pageFieldInfoSave.setPageId(pageInfoEntity.getId());
                pageFieldInfoService.save(pageFieldInfoSave);
            }
        }
    }

    @LenLock(key = "'page-info-modify-'.concat(#saveDTO.id)")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(PageInfoSaveDTO saveDTO) {
        //1. 参数校验
        Assert.notNull(saveDTO);
        Assert.notNull(saveDTO.getId(),"id");
        Assert.notEmpty(saveDTO.getPageName(), "页面名称");

        //2. 修改分页页面
        PageInfoEntity pageInfoEntity = new PageInfoEntity();
        pageInfoEntity.setId(saveDTO.getId());
        pageInfoEntity.setPageName(saveDTO.getPageName());
        pageInfoEntity.setPageUrl(saveDTO.getPageUrl());
        pageInfoEntity.setUpdater("");
        pageInfoEntity.setUpdateTime(LocalDateTime.now());
        this.updateById(pageInfoEntity);

        //3. 修改字段
        pageFieldInfoService.modifyPageFields(saveDTO.getId(), saveDTO.getPageFieldInfoSaves());
    }

    @Override
    public void delete(Integer id) {
        Assert.notNull(id);
        PageInfoEntity pageInfoEntity = new PageInfoEntity();
        pageInfoEntity.setId(id);
        pageInfoEntity.setIsDel(1);
        pageInfoEntity.setUpdateTime(LocalDateTime.now());
        pageInfoEntity.setUpdater("");
        this.updateById(pageInfoEntity);
        //删除字段信息
        pageFieldInfoService.delete(id, null);
    }
}
