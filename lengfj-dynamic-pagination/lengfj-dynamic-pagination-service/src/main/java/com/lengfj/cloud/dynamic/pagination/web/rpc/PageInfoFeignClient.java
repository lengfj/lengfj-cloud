package com.lengfj.cloud.dynamic.pagination.web.rpc;

import com.lengfj.cloud.dynamic.pagination.model.vo.PageInfoVO;
import com.lengfj.cloud.dynamic.pagination.service.PageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/rpc/pageInfo")
@RestController
public class PageInfoFeignClient {

    @Autowired
    private PageInfoService pageInfoService;

    /***
     * 列表
     *
     * @param pageCode 页面code
     * @param pageName 页面名称
     * @author lengfj
     * @date 2022/1/11 9:14 AM
     */
    @GetMapping("/list")
    public List<PageInfoVO> list(@RequestParam(value = "pageCode", required = false) String pageCode,
                                    @RequestParam(value = "pageName", required = false) String pageName){
        return pageInfoService.list(pageCode, pageName);
    }
}
