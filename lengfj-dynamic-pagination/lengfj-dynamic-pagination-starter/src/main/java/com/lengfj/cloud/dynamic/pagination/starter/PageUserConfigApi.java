package com.lengfj.cloud.dynamic.pagination.starter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "lengfj-dynamic-pagination-api", path = "/lengfj-dynamic-pagination-api/rpc/pageUserConfig")
public interface PageUserConfigApi {

    /**
     * 获取用户页面配置列表
     * @param userId 主体id
     * @param pageId 页面id
     * @return 用户页面配置
     * @author lengfj
     * @date 2022/1/11 7:48 PM
     */
    @GetMapping("/configList")
    List<PageListConfigVO> configList(@RequestParam("userId") Long userId, @RequestParam("pageId") Integer pageId);

    /**
     * 获取默认的页面配置列表
     * @param pageId 页面id
     * @return 默认页面配置
     * @author lengfj
     * @date 2022/1/11 7:48 PM
     */
    @GetMapping("/defaultConfigList")
    List<PageListConfigVO> defaultConfigList(@RequestParam("pageId") Integer pageId);

    /***
     * 保存或修改
     *
     * @param configDto 参数
     * @author lengfj
     * @date 2022/1/11 7:50 PM
     */
    @PostMapping("/saveOrModify")
    void saveOrModify(@RequestBody PageUserConfigDTO configDto);

}
