package com.lengfj.cloud.dynamic.pagination.web.rpc;

import com.lengfj.cloud.dynamic.pagination.model.dto.PageUserConfigDTO;
import com.lengfj.cloud.dynamic.pagination.model.vo.PageListConfigVO;
import com.lengfj.cloud.dynamic.pagination.service.PageUserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/rpc/pageUserConfig")
@RestController
public class PageUserConfigFeignClient {

    @Autowired
    private PageUserConfigService pageUserConfigService;

    /**
     * 获取用户页面配置列表
     * @param userId 主体id
     * @param pageId 页面id
     * @return 用户页面配置
     * @author lengfj
     * @date 2022/1/11 7:48 PM
     */
    @GetMapping("/configList")
    public List<PageListConfigVO> configList(@RequestParam("userId") Long userId, @RequestParam("pageId") Integer pageId){
        return pageUserConfigService.configList(userId, pageId);
    }

    /**
     * 获取默认的页面配置列表
     * @param pageId 页面id
     * @return 默认页面配置
     * @author lengfj
     * @date 2022/1/11 7:48 PM
     */
    @GetMapping("/defaultConfigList")
    public List<PageListConfigVO> defaultConfigList(@RequestParam("pageId") Integer pageId){
        return pageUserConfigService.defaultConfigList(pageId);
    }

    /***
     * 保存或修改
     *
     * @param configDto 参数
     * @author lengfj
     * @date 2022/1/11 7:50 PM
     */
    @PostMapping("/saveOrModify")
    public void saveOrModify(@RequestBody PageUserConfigDTO configDto){
        pageUserConfigService.saveOrModify(configDto);
    }
}
