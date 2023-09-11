package com.lengfj.cloud.dynamic.pagination.starter.feign;

import com.lengfj.cloud.dynamic.pagination.starter.feign.vo.PageInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "lengfj-dynamic-pagination-api", path = "/lengfj-dynamic-pagination-api/rpc/pageInfo", contextId = "pageInfoApi")
public interface PageInfoApi {

    /***
     * 列表
     *
     * @param pageCode 页面code
     * @param pageName 页面名称
     * @author lengfj
     * @date 2022/1/11 9:14 AM
     */
    @GetMapping("/list")
    List<PageInfoVO> list(@RequestParam(value = "pageCode", required = false) String pageCode,
                          @RequestParam(value = "pageName", required = false) String pageName);
}
