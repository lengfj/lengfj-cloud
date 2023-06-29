package com.lengfj.cloud.dynamic.pagination.api;

import com.lengfj.cloud.common.core.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lengfj
 * @version 1.0
 * @date 2023/5/16
 **/
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/testHello")
    public R<Object> testHello(@RequestParam("name") String name){
        return R.success("hello " + name);
    }

}
