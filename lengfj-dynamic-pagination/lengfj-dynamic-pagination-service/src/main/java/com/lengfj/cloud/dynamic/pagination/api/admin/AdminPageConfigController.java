package com.lengfj.cloud.dynamic.pagination.api.admin;

import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigAddFieldDTO;
import com.lengfj.cloud.dynamic.pagination.model.dto.PageListConfigSaveDTO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO;
import com.lengfj.cloud.dynamic.pagination.service.PageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin/pageConfig")
@RestController
public class AdminPageConfigController {

    @Autowired
    private PageConfigService pageConfigService;

    /***
     * 查询页面配置列表
     *
     * @param configId
     * @return {@link List<com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO>}
     * @author lengfj
     * @date 2022/1/11 7:20 PM
     */
    @GetMapping("/list")
    public List<PageListConfigVO> list(@RequestParam("configId") Integer configId){
        return pageConfigService.list(configId);
    }

    /***
     * 保存
     *
     * @param saveDto 配置
     * @return
     * @author lengfj
     * @date 2022/1/11 7:22 PM
     */
    @PostMapping("/save")
    Integer save(@RequestBody PageListConfigSaveDTO saveDto){
        return pageConfigService.save(saveDto);
    }

    /**
     * 修改配置
     * @param saveDto 配置
     */
    void modify(PageListConfigSaveDTO saveDto){

    }

    /**
     * 修改配置
     * @param saveDtos 配置列表
     */
    @PostMapping("/modify")
    public void modify(List<PageListConfigSaveDTO> saveDtos){
        pageConfigService.modify(saveDtos);
    }

    /***
     * 删除页面字段
     *
     * @param pageId 页面id
     * @param fieldId 字段id
     * @return
     * @author lengfj
     * @date 2022/1/11 7:37 PM
     */
    void delField(Integer pageId, Integer fieldId){

    }

    /***
     * 添加页面字段
     *
     * @param addFieldDto 添加字段参数
     * @return
     * @author lengfj
     * @date 2022/1/11 7:37 PM
     */
    void addField(PageListConfigAddFieldDTO addFieldDto){

    }
}
